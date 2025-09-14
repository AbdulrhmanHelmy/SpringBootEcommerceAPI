package com.helmy.ecommerce.Repository;

import com.helmy.ecommerce.Model.Order.Order;
import com.helmy.ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

}
