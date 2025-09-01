package com.helmy.ecommerce.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Entity
@Getter
public class FavList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    private  List<Product> products;

    public void addItem(Product product) {
        if(products==null){
            products=new ArrayList<>();
        }
        if(products.contains(product)){
            throw new RuntimeException("This Product is Already In You Favourite List");
        }
        products.add(product);
    }

    public void removeItem(Product product) {
        products.remove(product);
    }



    public void deleteAll() {
        if (products != null) {
            products.clear();
        }
    }

}
