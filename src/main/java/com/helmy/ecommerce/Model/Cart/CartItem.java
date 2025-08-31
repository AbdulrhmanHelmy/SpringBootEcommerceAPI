package com.helmy.ecommerce.Model.Cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.helmy.ecommerce.Model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Entity
@Getter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    private Product product;

    private Integer quantity;
}
