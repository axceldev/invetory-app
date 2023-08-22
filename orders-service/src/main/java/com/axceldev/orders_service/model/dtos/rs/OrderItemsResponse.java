package com.axceldev.orders_service.model.dtos.rs;

public record OrderItemsResponse(Long idOrderItem, String sku, Double price, Long quantity) {
}
