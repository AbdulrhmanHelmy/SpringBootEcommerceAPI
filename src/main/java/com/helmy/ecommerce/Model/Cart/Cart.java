package com.helmy.ecommerce.Model.Cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.helmy.ecommerce.Exeption.ResourceNotFound;
import com.helmy.ecommerce.Model.Product;
import com.helmy.ecommerce.Model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Entity
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CartItem> cartItemList;

    public boolean hasStock(Product p, int q) {
        return p.getStock() < q;
    }

    public void addItem(Product product, int quantity) {
        if (hasStock(product, quantity)) {
            throw new ResourceNotFound("Above Our Stock");
        }
        CartItem existingItem = cartItemList.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);

        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(this);
            cartItemList.add(newItem);
        }
    }

    public void removeItem(Product product) {
        cartItemList.removeIf(item -> item.getProduct().equals(product));
    }

    public void updateQuantity(Product product, int quantity) {
        if (hasStock(product, quantity)) {
            throw new ResourceNotFound("Above Our Stock");
        }
        CartItem existingItem = cartItemList.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            if (quantity <= 0) {
                cartItemList.remove(existingItem);
            } else {
                existingItem.setQuantity(quantity);
            }
        }
    }

    public void deleteAll() {
        if (cartItemList != null) {
            cartItemList.clear();
        }
    }

}
