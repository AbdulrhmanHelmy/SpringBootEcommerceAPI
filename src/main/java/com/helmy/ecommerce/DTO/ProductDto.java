package com.helmy.ecommerce.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ProductDto {
    private String name;
    private String description;
    private Integer stock;
    private Double price;
    private String currency = "EGP";
    private Boolean isAvailable;
    private CategoryDto categoryDto;
}
