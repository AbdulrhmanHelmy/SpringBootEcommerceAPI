package com.helmy.ecommerce.Service.order;

import com.helmy.ecommerce.Model.Order.Order;
import com.helmy.ecommerce.Model.User;

import java.util.List;

public interface OrderService {
    Order CheckOut(String address, String phone1, String phone2, String couponCode);

    List<Order> getOrdersByUser(User user);

    Order getOrderById(Long id);

    void deleteOrder(Long id);

    Order updateOrder(Long id, String newAddress, String phone1, String phone2, String couponCode);

    void cancelOrder(Long id);

    List<Order> getAllOrders();


}
