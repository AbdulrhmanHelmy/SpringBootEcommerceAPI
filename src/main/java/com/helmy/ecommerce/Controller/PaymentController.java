package com.helmy.ecommerce.Controller;

import com.helmy.ecommerce.Model.Order.Order;
import com.helmy.ecommerce.Model.Payment;
import com.helmy.ecommerce.Service.PaymentStrategy.PaymentStrategy;
import com.helmy.ecommerce.Service.order.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentStrategy paymentStrategy;
    private final OrderService orderService;

    public PaymentController(PaymentStrategy paymentStrategy, OrderService orderService) {
        this.paymentStrategy = paymentStrategy;
        this.orderService = orderService;
    }

    @PostMapping("/pay/test")
    public Payment payTest(@RequestBody Long orderId){
        Order order = orderService.getOrderById(orderId);
        return paymentStrategy.pay(order);
    }
}
