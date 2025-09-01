package com.helmy.ecommerce.Service.Coupon;

import com.helmy.ecommerce.DTO.CouponDto;
import com.helmy.ecommerce.Model.Coupon;
import com.helmy.ecommerce.Model.Order.Order;

public interface CouponService {
    Coupon create(CouponDto couponDto);

    Coupon get(String code);

    Coupon use(Order order, String code);

}
