package com.axceldev.notification_service.listeners;

import com.axceldev.notification_service.events.OrderEvent;
import com.axceldev.notification_service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventListener {

    @KafkaListener(topics = "order-topic")
    public void handleOrderNotification(String message){
        var orderEvent = JsonUtils.fromJson(message, OrderEvent.class);
        //Send email to client, send SMS to customer, etc.
        //Notify another service
        log.info("Order {} event received for order: {} with {} items", orderEvent.orderStatus(), orderEvent.orderNumber(), orderEvent.itemCount());
    }

}
