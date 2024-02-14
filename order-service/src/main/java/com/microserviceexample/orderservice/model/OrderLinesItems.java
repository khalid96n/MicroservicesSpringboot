package com.microserviceexample.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "t_orders_lines_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLinesItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
