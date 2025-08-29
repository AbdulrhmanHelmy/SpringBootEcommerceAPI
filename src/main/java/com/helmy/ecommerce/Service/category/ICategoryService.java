package com.helmy.ecommerce.Service.category;

import com.helmy.ecommerce.DTO.CategoryDto;
import com.helmy.ecommerce.Model.Category;
import java.util.List;

public interface ICategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category create(CategoryDto categoryDto);

    Category update(Long id, CategoryDto categoryDto);

    void delete(Long id);

    Category findByName(String name);

    Category findByIdWithProducts(Long id);
}
