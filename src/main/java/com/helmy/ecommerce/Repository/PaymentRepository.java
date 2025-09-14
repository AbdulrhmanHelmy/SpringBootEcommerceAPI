package com.helmy.ecommerce.Repository;

import com.helmy.ecommerce.Model.ENUMS.PaymentMethod;
import com.helmy.ecommerce.Model.ENUMS.PaymentStatus;
import com.helmy.ecommerce.Model.Order.Order;
import com.helmy.ecommerce.Model.Payment;
import com.helmy.ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrder(Order order);
    List<Payment> findByUser(User user);
    List<Payment> findByStatus(PaymentStatus paymentStatus);
    List<Payment> findByMethod(PaymentMethod paymentMethod);
    List<Payment> findByDateBetween(Date startDate, Date endDate);
    List<Payment> findByAmountBetween(Double minAmount, Double maxAmount);
    List<Payment> findByDate(Date paymentDate);

}
