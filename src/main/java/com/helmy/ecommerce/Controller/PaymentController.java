package com.helmy.ecommerce.Controller;

import com.helmy.ecommerce.Model.Order.Order;
import com.helmy.ecommerce.Model.Payment;
import com.helmy.ecommerce.Response.API_Response;
import com.helmy.ecommerce.Service.PaymentStrategy.PaymentStrategy;
import com.helmy.ecommerce.Service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentStrategy paymentStrategy;
    private final OrderService orderService;

    public PaymentController(PaymentStrategy paymentStrategy, OrderService orderService) {
        this.paymentStrategy = paymentStrategy;
        this.orderService = orderService;
    }

    @PostMapping("/test")
    public ResponseEntity<API_Response> payTest(@RequestParam Long orderId){
        Order order = orderService.getOrderById(orderId);
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Success Payment");
        apiResponse.setData(paymentStrategy.pay(order));
        return ResponseEntity.ok(apiResponse);
    }
}
