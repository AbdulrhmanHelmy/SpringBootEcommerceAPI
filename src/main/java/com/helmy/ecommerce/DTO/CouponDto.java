package com.helmy.ecommerce.DTO;

import com.helmy.ecommerce.Model.ENUMS.DiscountType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CouponDto {
    private String code;

    private DiscountType discountType;

    private Double discountValue;

    private LocalDate expiryDate;

    private Double minOrderValue;

    private Integer maxUsage;

}
