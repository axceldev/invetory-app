package com.axceldev.products_service.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;

    private String sku;
    private String name;
    private String description;
    private Double price;
    private boolean status;

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
