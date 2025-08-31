package com.helmy.ecommerce.Repository;

import com.helmy.ecommerce.Model.Cart.Cart;
import com.helmy.ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUser(User user);
}
