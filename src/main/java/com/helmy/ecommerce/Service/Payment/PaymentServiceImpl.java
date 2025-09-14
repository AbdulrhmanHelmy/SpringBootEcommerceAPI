package com.helmy.ecommerce.Service.Payment;

import com.helmy.ecommerce.Model.ENUMS.PaymentMethod;
import com.helmy.ecommerce.Model.Order.Order;
import com.helmy.ecommerce.Model.Payment;
import com.helmy.ecommerce.Model.User;
import com.helmy.ecommerce.Repository.OrderRepository;
import com.helmy.ecommerce.Repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Payment createPayment(Order order) {
        Payment payment=new Payment();
        payment.setAmount(order.getTotalAmount());
        payment.setOrder(order);
        payment.setDate(new Date());
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public Payment deletePayment(Payment payment) {
        return null;
    }

    @Override

    public Payment getPaymentById(Long id) {
        return null;
    }

    @Override
    public List<Payment> getAllPayments() {
        return List.of();
    }

    @Override
    public List<Payment> getPaymentsByUser(User user) {
        return List.of();
    }

    @Override
    public Payment getPaymentByOrder(Order order) {
        return null;
    }
}
