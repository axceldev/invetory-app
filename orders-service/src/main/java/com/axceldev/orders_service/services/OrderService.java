package com.axceldev.orders_service.services;

import com.axceldev.orders_service.events.OrderEvent;
import com.axceldev.orders_service.model.dtos.rq.OrderItemRequest;
import com.axceldev.orders_service.model.dtos.rq.OrderRequest;
import com.axceldev.orders_service.model.dtos.rs.BaseResponse;
import com.axceldev.orders_service.model.dtos.rs.OrderItemsResponse;
import com.axceldev.orders_service.model.dtos.rs.OrderResponse;
import com.axceldev.orders_service.model.entities.Order;
import com.axceldev.orders_service.model.entities.OrderItems;
import com.axceldev.orders_service.model.enums.OrderStatus;
import com.axceldev.orders_service.repositories.OrderRepository;
import com.axceldev.orders_service.utils.JsonUtils;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public static final String URL_CHECK_IN_STOCK = "lb://inventory-service/api/v1/inventory/in-stock";
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    public OrderResponse placeOrder(OrderRequest orderRequest) {

        Observation inventoryObservation = Observation
                .createNotStarted("inventory-service", observationRegistry);

        //TODO: check for inventary
        return inventoryObservation.observe( () -> {
            BaseResponse response = this.webClientBuilder.build()
                    .post()
                    .uri(URL_CHECK_IN_STOCK)
                    .bodyValue(orderRequest.getOrderItem())
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();

            if (response != null && !response.hasErrors()) {
                Order order = new Order();
                order.setOrderNumber(UUID.randomUUID().toString());
                order.setOrderItems(orderRequest.getOrderItem().stream()
                        .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                        .toList());
                var saveOrder = this.orderRepository.save(order);
                //TODO: Send message to order topic
                this.kafkaTemplate.send("order-topic", JsonUtils
                        .toJson(new OrderEvent(saveOrder.getOrderNumber(), saveOrder.getOrderItems().size(), OrderStatus.PLACED)));

                return mapToOrderResponse(saveOrder);
            } else {
                throw new IllegalArgumentException("Some of the products are not stock");
            }
        });

    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        return OrderItems.builder()
                .idOrderItem(orderItemRequest.getIdOrderItem())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }

    public List<OrderResponse> getAllOrder(){
        List<Order> orders =  this.orderRepository.findAll();
        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(order.getIdOrder(), order.getOrderNumber(),
                order.getOrderItems().stream().map(this::mapToOrderItemResponse).toList()
        );
    }

    private OrderItemsResponse mapToOrderItemResponse(OrderItems orderItems) {
        return new OrderItemsResponse(
                orderItems.getIdOrderItem(),
                orderItems.getSku(),
                orderItems.getPrice(),
                orderItems.getQuantity()
        );
    }


}
