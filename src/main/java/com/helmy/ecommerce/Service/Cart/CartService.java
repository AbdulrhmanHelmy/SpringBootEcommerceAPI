package com.helmy.ecommerce.Service.Cart;

import com.helmy.ecommerce.Model.Cart.Cart;
import com.helmy.ecommerce.Model.Product;
import com.helmy.ecommerce.Model.User;

public interface CartService {


        Cart getOrCreateCart(User user);

        Cart addItemToCart(User user, Product product, int quantity);

        Cart removeItemFromCart(User user, Product product);

        Cart updateItemQuantity(User user, Product product, int quantity);

        Cart deleteAllProducts(User user);

}
