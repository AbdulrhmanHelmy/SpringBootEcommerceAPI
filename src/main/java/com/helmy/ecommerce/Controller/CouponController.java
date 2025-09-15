package com.helmy.ecommerce.Controller;

import com.helmy.ecommerce.DTO.CouponDto;
import com.helmy.ecommerce.Response.API_Response;
import com.helmy.ecommerce.Service.Coupon.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/coupon")
public class CouponController {
    private final CouponService couponService;


    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<API_Response> createCoupon(@RequestBody CouponDto couponDto) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Coupon created successfully");
        apiResponse.setData(couponService.create(couponDto));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<API_Response> getAllCoupons() {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("All Coupons");
        apiResponse.setData(couponService.getAll());
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<API_Response> updateCoupon(@PathVariable Long id, @RequestBody CouponDto couponDto) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Coupon updated successfully");
        apiResponse.setData(couponService.update(id, couponDto));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<API_Response> getCouponById(@PathVariable Long id) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Coupon retrieved successfully");
        apiResponse.setData(couponService.getById(id));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/code")
    public ResponseEntity<API_Response> getCouponByCode(@RequestParam String code) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Coupon retrieved successfully");
        apiResponse.setData(couponService.get(code));
        return ResponseEntity.ok(apiResponse);
    }

}
