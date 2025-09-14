package com.helmy.ecommerce.Controller;

import com.helmy.ecommerce.DTO.OrderDTO;
import com.helmy.ecommerce.Response.API_Response;
import com.helmy.ecommerce.Service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<API_Response> checkout(@RequestBody OrderDTO orderDTO) {
        API_Response response = new API_Response();
        response.setData(orderService.CheckOut(orderDTO.getAddress(), orderDTO.getPhone1(), orderDTO.getPhone2(), orderDTO.getCouponCode()));
        response.setMessage("Checkout successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<API_Response> getAllOrders() {
        API_Response response = new API_Response();
        response.setData(orderService.getAllOrders());
        response.setMessage("All orders");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<API_Response> getOrderById(@PathVariable Long id) {
        API_Response response = new API_Response();
        response.setData(orderService.getOrderById(id));
        response.setMessage("Order retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<API_Response> cancelOrder(@PathVariable Long id) {
        API_Response response = new API_Response();
        orderService.cancelOrder(id);
        response.setMessage("Order canceled successfully");
        response.setData(null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<API_Response> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long id) {
        API_Response response = new API_Response();
        response.setData(orderService.updateOrder(id,orderDTO.getAddress(), orderDTO.getPhone1(), orderDTO.getPhone2(), orderDTO.getCouponCode()));
        response.setMessage("Order updated successfully");
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<API_Response> deleteOrder(@PathVariable Long id) {
        API_Response response = new API_Response();
        orderService.deleteOrder(id);
        response.setMessage("Order deleted successfully");
        response.setData(null);
        return ResponseEntity.ok(response);
    }


}
