package com.helmy.ecommerce.Service.PaymentStrategy;

import com.helmy.ecommerce.Model.ENUMS.PaymentMethod;
import com.helmy.ecommerce.Model.Order.Order;
import com.helmy.ecommerce.Model.Payment;
import com.helmy.ecommerce.Service.Payment.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class TestPay implements PaymentStrategy {
    private final PaymentService paymentService;

    public TestPay(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public Payment pay(Order order) {
        System.out.println("Payment processed successfully");
        System.out.println("Some operations for  payment ");
        return paymentService.pay(order,PaymentMethod.TEST);
    }

    @Override
    public Payment refund(Payment payment) {
        return null;
    }
}
