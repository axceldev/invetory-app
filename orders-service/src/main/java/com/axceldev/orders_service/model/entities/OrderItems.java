package com.axceldev.orders_service.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_item")
    private Long idOrderItem;

    private String sku;

    private Double price;

    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


}
