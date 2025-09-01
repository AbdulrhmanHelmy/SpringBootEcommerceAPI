package com.helmy.ecommerce.Model.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.helmy.ecommerce.Model.Coupon;
import com.helmy.ecommerce.Model.ENUMS.OrderStatus;
import com.helmy.ecommerce.Model.ENUMS.PaymentMethod;
import com.helmy.ecommerce.Model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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

    @OneToMany
    private List<OrderItem> orderItems;


    @ManyToOne
    private Coupon coupon;

    private String address;

    private String phone1;

    private String phone2;

    private Double totalAmount;

    private OrderStatus orderStatus=OrderStatus.NEW;

    private PaymentMethod paymentMethod;





}
