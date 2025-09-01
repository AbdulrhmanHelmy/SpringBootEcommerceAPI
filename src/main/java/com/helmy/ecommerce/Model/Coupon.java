package com.helmy.ecommerce.Model;

import com.helmy.ecommerce.Model.ENUMS.DiscountType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;


    private DiscountType discountType;

    private Double discountValue;

    private LocalDate expiryDate;

    private Double minOrderValue;

    private Integer maxUsage;

    private Integer usedCount = 0;



}
