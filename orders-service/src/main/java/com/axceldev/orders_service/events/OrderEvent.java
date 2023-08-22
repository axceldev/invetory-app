package com.axceldev.orders_service.events;

import com.axceldev.orders_service.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemCount, OrderStatus orderStatus) {

}
