package com.helmy.ecommerce.Service.order;

import com.helmy.ecommerce.Exeption.ResourceNotFound;
import com.helmy.ecommerce.Model.Cart.Cart;
import com.helmy.ecommerce.Model.Cart.CartItem;
import com.helmy.ecommerce.Model.ENUMS.OrderStatus;
import com.helmy.ecommerce.Model.ENUMS.PaymentStatus;
import com.helmy.ecommerce.Model.ENUMS.Role;
import com.helmy.ecommerce.Model.Order.Order;
import com.helmy.ecommerce.Model.Order.OrderItem;
import com.helmy.ecommerce.Model.Payment;
import com.helmy.ecommerce.Model.Product;
import com.helmy.ecommerce.Model.User;
import com.helmy.ecommerce.Repository.OrderRepository;
import com.helmy.ecommerce.Repository.PaymentRepository;
import com.helmy.ecommerce.Service.Cart.CartService;
import com.helmy.ecommerce.Service.Coupon.CouponService;
import com.helmy.ecommerce.Service.Product.ProductServiceImpl;
import com.helmy.ecommerce.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceIMPL implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CouponService couponService;
    private final UserService userService;
    private final ProductServiceImpl productService;
    private final PaymentRepository paymentRepository;

    @Autowired
    public OrderServiceIMPL(OrderRepository orderRepository, CartService cartService, CouponService couponService, UserService userService, ProductServiceImpl productService, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.couponService = couponService;
        this.userService = userService;
        this.productService = productService;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public Order CheckOut(String address, String phone1, String phone2, String couponCode) {
        User user = userService.getCurrentUser();
        Cart cart = cartService.getOrCreateCart(user);
        Payment payment = new Payment();
        Order order = new Order();
        payment.setStatus(PaymentStatus.PENDING);
        payment.setUser(user);
        payment.setDate(new Date());
        payment.setOrder(order);
        order.setOrderStatus(OrderStatus.NEW);
        order.setTotalAmount(0.0);
        order.setPayment(payment);
        for (CartItem c : cart.getCartItemList()) {
            Product product = c.getProduct();
            if (product.getStock() < c.getQuantity()) {
                throw new RuntimeException("Your Quantity is more than available stock for ProductId: " + product.getId() + "  is out of stock The remain Stock is" + product.getStock());
            }
            productService.UpdateTheStock(product, c.getQuantity(), false);
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setQuantity(c.getQuantity());
            orderItem.setPrice(product.getPrice());
            order.getOrderItems().add(orderItem);
            order.setTotalAmount(order.getTotalAmount() + (orderItem.getPrice() * orderItem.getQuantity()));
        }
        order.setUser(user);
        order.setAddress(address);
        order.setPhone1(phone1);
        order.setPhone2(phone2);
        if (couponCode != null && !couponCode.isEmpty()) {
            order.setCoupon(couponService.get(couponCode));
            Double discount = couponService.use(order, couponCode);
            order.setTotalAmount(order.getTotalAmount() - discount);
        }
        payment.setAmount(order.getTotalAmount());
        cartService.deleteAllProducts(user);
        orderRepository.save(order);
        paymentRepository.save(payment);
        return order;
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public Order getOrderById(Long id) {
        User user = userService.getCurrentUser();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Order not found with id " + id));
        if (order.getUser().getId().equals(user.getId()) || user.getRole().equals(Role.ADMIN)) {
            return order;
        } else {
            throw new RuntimeException("You cannot access this order");
        }
    }


    @Override
    @Transactional

    public void cancelOrder(Long id) {
        Order order = getOrderById(id);
        User user = userService.getCurrentUser();
        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("You cannot cancel this order");
        }
        order.setOrderStatus(OrderStatus.CANCELED);
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            productService.UpdateTheStock(product, orderItem.getQuantity(), true);
        }
        if (order.getCoupon() != null) {
            couponService.cancel(order.getCoupon().getCode());
        }
        if (order.getPayment().getStatus() == PaymentStatus.PAID) {
            order.getPayment().setStatus(PaymentStatus.REFUNDED);
            User user1 = order.getUser();
            userService.addBalance(user1, order.getTotalAmount());
        }
        orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        User user = userService.getCurrentUser();
        return getOrdersByUser(user);
    }

    @Override
    @Transactional

    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        User user = userService.getCurrentUser();
        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("You cannot delete this order");
        }
        orderRepository.delete(order);
    }

    @Override
    @Transactional

    public Order updateOrder(Long id, String newAddress, String phone1, String phone2, String couponCode) {
        Order order = getOrderById(id);
        User user = userService.getCurrentUser();
        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("You cannot update this order");
        }
        if (order.getOrderStatus() == OrderStatus.SHIPPED || order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Order cannot be updated after it is shipped or delivered.");
        }

        if (newAddress != null && !newAddress.isEmpty()) {
            order.setAddress(newAddress);
        }

        if (phone1 != null && !phone1.isEmpty()) {
            order.setPhone1(phone1);
        }

        if (phone2 != null && !phone2.isEmpty()) {
            order.setPhone2(phone2);
        }

        if (couponCode != null && !couponCode.isEmpty()) {
            order.setCoupon(couponService.get(couponCode));
            Double discount = couponService.use(order, couponCode);
            order.setTotalAmount(order.getTotalAmount() - discount);
        }

        return orderRepository.save(order);
    }

}
