package com.axceldev.products_service.model.dtos.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Long idProduct;
    private String sku;
    private String name;
    private String description;
    private Double price;
    private boolean status;
}
