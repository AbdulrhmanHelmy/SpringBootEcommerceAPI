package com.helmy.ecommerce.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Double price;
    private String currency = "EGP";

    private Boolean isAvailable;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany( fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
}
