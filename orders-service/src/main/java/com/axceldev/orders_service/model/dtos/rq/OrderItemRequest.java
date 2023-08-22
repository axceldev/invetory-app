package com.axceldev.orders_service.model.dtos.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {

    private Long idOrderItem;
    private String sku;
    private Double price;
    private Long quantity;

}
