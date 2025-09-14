package com.helmy.ecommerce.Service.Coupon;

import com.helmy.ecommerce.DTO.CouponDto;
import com.helmy.ecommerce.Exeption.ResourceNotFound;
import com.helmy.ecommerce.Model.Coupon;
import com.helmy.ecommerce.Model.ENUMS.DiscountType;
import com.helmy.ecommerce.Model.Order.Order;
import com.helmy.ecommerce.Repository.CouponRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CouponSeviceIMPL implements CouponService {
    private final CouponRepository couponRepository;

    public CouponSeviceIMPL(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    @Transactional
    public Coupon create(CouponDto couponDto) {
        if (couponRepository.findByCode(couponDto.getCode().toUpperCase()).isPresent()) {
            throw new IllegalArgumentException("Coupon code already exists");
        }
        Coupon coupon = new Coupon();
        coupon.setCode(couponDto.getCode().toUpperCase());
        coupon.setDiscountType(couponDto.getDiscountType());
        coupon.setDiscountValue(couponDto.getDiscountValue());
        coupon.setMaxUsage(couponDto.getMaxUsage());
        coupon.setMinOrderValue(couponDto.getMinOrderValue());
        coupon.setExpiryDate(couponDto.getExpiryDate());
        return couponRepository.save(coupon);
    }

    @Override
    @Transactional
    public Coupon get(String code) {
        return couponRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new ResourceNotFound(" This Code Does Not Exist"));
    }

    @Override
    @Transactional
    public Double use(Order order, String code) {
        Coupon coupon = get(code);

        if (order.getTotalAmount() < coupon.getMinOrderValue()) {
            throw new IllegalStateException("Order amount is less than the minimum required for this coupon");
        }

        if (coupon.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("This coupon has expired");
        }

        if (coupon.getUsedCount() >= coupon.getMaxUsage()) {
            throw new IllegalStateException("This coupon has reached its maximum usage limit");
        }


        coupon.setUsedCount(coupon.getUsedCount() + 1);
        couponRepository.save(coupon);

        if (coupon.getDiscountType().equals(DiscountType.PERCENTAGE)) {
            return order.getTotalAmount() * coupon.getDiscountValue() / 100;
        } else {
            return coupon.getDiscountValue();
        }
    }

    @Override
    @Transactional
    public void cancel(String code) {
        Coupon c=get(code);
        c.setUsedCount(c.getUsedCount()-1);
        couponRepository.save(c);
    }

}
