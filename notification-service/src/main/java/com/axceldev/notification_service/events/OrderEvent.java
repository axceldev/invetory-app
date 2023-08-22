package com.axceldev.notification_service.events;


import com.axceldev.notification_service.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemCount, OrderStatus orderStatus) {

}
