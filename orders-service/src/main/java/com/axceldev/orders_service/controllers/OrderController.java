package com.axceldev.orders_service.controllers;

import com.axceldev.orders_service.model.dtos.rq.OrderRequest;
import com.axceldev.orders_service.model.dtos.rs.OrderResponse;
import com.axceldev.orders_service.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "orders-service", fallbackMethod = "placeOrderFallBack")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){
        var orders = this.orderService.placeOrder(orderRequest);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders(){
        return orderService.getAllOrder();
    }

    private ResponseEntity<OrderResponse> placeOrderFallBack(OrderRequest orderRequest, Throwable throwable){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }



}
