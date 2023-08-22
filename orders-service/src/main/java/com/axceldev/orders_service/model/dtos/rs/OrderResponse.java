package com.axceldev.orders_service.model.dtos.rs;

import java.util.List;

public record OrderResponse(Long idOrder, String orderNumber, List<OrderItemsResponse> orderItems) {
}
