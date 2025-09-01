package com.helmy.ecommerce.Model.Order;

import com.helmy.ecommerce.Model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;

    private Double price;

    private int quantity;
    @ManyToOne
    private Order order;

}
