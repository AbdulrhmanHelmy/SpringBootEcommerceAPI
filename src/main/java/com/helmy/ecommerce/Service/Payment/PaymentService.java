package com.helmy.ecommerce.Service.Payment;

import com.helmy.ecommerce.Model.ENUMS.PaymentMethod;
import com.helmy.ecommerce.Model.Order.Order;
import com.helmy.ecommerce.Model.Payment;
import com.helmy.ecommerce.Model.User;
import java.util.List;

public interface PaymentService {
    Payment pay(Order order, PaymentMethod paymentMethod);
    Payment deletePayment(Payment payment);
    Payment getPaymentById(Long id);
    List<Payment> getAllPayments();
    List<Payment> getPaymentsByUser(User user);
    Payment getPaymentByOrder(Order order);


}
