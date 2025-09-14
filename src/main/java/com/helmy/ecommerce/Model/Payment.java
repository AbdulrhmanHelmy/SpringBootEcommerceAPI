package com.helmy.ecommerce.Model;

import com.helmy.ecommerce.Model.ENUMS.PaymentMethod;
import com.helmy.ecommerce.Model.ENUMS.PaymentStatus;
import com.helmy.ecommerce.Model.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @OneToOne
    private Order order;
    private Double amount;

    private Date date;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

}
