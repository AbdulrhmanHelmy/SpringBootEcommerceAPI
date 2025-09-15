package com.helmy.ecommerce.Model.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.helmy.ecommerce.Model.Coupon;
import com.helmy.ecommerce.Model.ENUMS.OrderStatus;
import com.helmy.ecommerce.Model.Payment;
import com.helmy.ecommerce.Model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    @ManyToOne
    private Coupon coupon;

    private String address;

    private String phone1;

    private String phone2;

    private Double totalAmount;

    private OrderStatus orderStatus = OrderStatus.NEW;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private Payment payment;


}
