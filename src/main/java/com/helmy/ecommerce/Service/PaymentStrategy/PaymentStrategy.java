package com.helmy.ecommerce.Service.PaymentStrategy;

import com.helmy.ecommerce.Model.Order.Order;
import com.helmy.ecommerce.Model.Payment;

public interface PaymentStrategy {
    Payment pay(Order order);
    Payment refund(Payment payment);

}
