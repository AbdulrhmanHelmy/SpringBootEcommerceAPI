package com.helmy.ecommerce.Service.Cart;

import com.helmy.ecommerce.Model.Cart.Cart;
import com.helmy.ecommerce.Model.Cart.CartItem;
import com.helmy.ecommerce.Model.Product;
import com.helmy.ecommerce.Model.User;
import com.helmy.ecommerce.Repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CartServiceIMPL implements CartService {
    private final CartRepository cartRepository;

    public CartServiceIMPL(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public Cart getOrCreateCart(User user) {
        Cart c = cartRepository.findByUser(user);
        if (c == null) {
            c = new Cart();
            c.setCartItemList(new ArrayList<>());
            c.setUser(user);
            cartRepository.save(c);
        }
        return c;
    }


    @Override
    public Cart addItemToCart(User user, Product product, int quantity) {
        Cart c = getOrCreateCart(user);
        c.addItem(product,quantity);
        return cartRepository.save(c);
    }

    @Override
    public Cart removeItemFromCart(User user, Product product) {
        Cart c = getOrCreateCart(user);
        c.removeItem(product);
       return cartRepository.save(c);
    }

    @Override
    public Cart updateItemQuantity(User user, Product product, int quantity) {
        Cart c = getOrCreateCart(user);
        c.updateQuantity(product,quantity);
       return cartRepository.save(c);
    }

    @Override
    public Cart deleteAllProducts(User user) {
        Cart c = getOrCreateCart(user);
        c.deleteAll();
       return cartRepository.save(c);
    }
}
