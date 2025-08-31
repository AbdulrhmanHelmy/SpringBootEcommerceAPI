package com.helmy.ecommerce.Controller;

import com.helmy.ecommerce.Exeption.ResourceNotFound;
import com.helmy.ecommerce.Model.Cart.Cart;
import com.helmy.ecommerce.Model.Product;
import com.helmy.ecommerce.Model.User;
import com.helmy.ecommerce.Repository.ProductRepository;
import com.helmy.ecommerce.Repository.UserRepository;
import com.helmy.ecommerce.Response.API_Response;
import com.helmy.ecommerce.Service.Cart.CartServiceIMPL;
import com.helmy.ecommerce.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartServiceIMPL cartService;
    private final ProductRepository productRepository;
    private final UserService userService;

    public CartController(CartServiceIMPL cartService, ProductRepository productRepository, UserRepository userRepository, UserService userService) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<API_Response> get() {
        User u = userService.getCurrentUser();
        Cart c = cartService.getOrCreateCart(u);
        API_Response apiResponse = new API_Response("Cart Founded Successfully", c);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<API_Response> addItem( @RequestParam("productId") Long pid, @RequestParam("quantity") int quantity) {
        User u = userService.getCurrentUser();
        Product p = productRepository.findById(pid).orElseThrow(
                () -> new ResourceNotFound("Product Not found"));
        API_Response apiResponse = new API_Response("Cart Founded Successfully", null);
        Cart c = cartService.addItemToCart(u, p, quantity);
        apiResponse.setData(c);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/remove")
    public ResponseEntity<API_Response> removeItem(@RequestParam("productId") Long productId) {
        User u = userService.getCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product Not found"));

        cartService.removeItemFromCart(u, product);
        Cart cart = cartService.getOrCreateCart(u);
        return ResponseEntity.ok(new API_Response("Item removed successfully", cart));
    }

    @PostMapping("/update")
    public ResponseEntity<API_Response> updateItem(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") int quantity) {
        User u = userService.getCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product Not found"));

        cartService.updateItemQuantity(u, product, quantity);
        Cart cart = cartService.getOrCreateCart(u);
        return ResponseEntity.ok(new API_Response("Quantity updated successfully", cart));
    }

    @PostMapping("/clear")
    public ResponseEntity<API_Response> clearCart() {
        User u = userService.getCurrentUser();
        cartService.deleteAllProducts(u);
        Cart cart = cartService.getOrCreateCart(u);
        return ResponseEntity.ok(new API_Response("Cart cleared successfully", cart));
    }

}
