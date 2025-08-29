package com.helmy.ecommerce.Controller;

import com.helmy.ecommerce.DTO.CategoryDto;
import com.helmy.ecommerce.Model.Category;
import com.helmy.ecommerce.Response.API_Response;
import com.helmy.ecommerce.Service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    // Create a new category
    @PostMapping
    public ResponseEntity<API_Response> createCategory(@RequestBody CategoryDto categoryDto) {
        Category created = categoryService.create(categoryDto);
        API_Response response = new API_Response();
        response.setMessage("Category created successfully");
        response.setData(created);
        return ResponseEntity.ok(response);
    }

    // Update an existing category
    @PutMapping("/{id}")
    public ResponseEntity<API_Response> updateCategory(@PathVariable Long id,
                                                       @RequestBody CategoryDto categoryDto) {
        Category updated = categoryService.update(id, categoryDto);
        API_Response response = new API_Response();
        response.setMessage("Category updated successfully");
        response.setData(updated);
        return ResponseEntity.ok(response);
    }

    // Delete a category
    @DeleteMapping("/{id}")
    public ResponseEntity<API_Response> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        API_Response response = new API_Response();
        response.setMessage("Category deleted successfully");
        response.setData(null);
        return ResponseEntity.ok(response);
    }

    // Get all categories
    @GetMapping
    public ResponseEntity<API_Response> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        API_Response response = new API_Response();
        response.setMessage("Categories retrieved successfully");
        response.setData(categories);
        return ResponseEntity.ok(response);
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<API_Response> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        API_Response response = new API_Response();
        response.setMessage("Category retrieved successfully");
        response.setData(category);
        return ResponseEntity.ok(response);
    }

    // Get category by name
    @GetMapping("/search")
    public ResponseEntity<API_Response> getCategoryByName(@RequestParam String name) {
        Category category = categoryService.findByName(name);
        API_Response response = new API_Response();
        response.setMessage("Category retrieved successfully");
        response.setData(category);
        return ResponseEntity.ok(response);
    }

    // Get category by ID with products
    @GetMapping("/{id}/products")
    public ResponseEntity<API_Response> getCategoryWithProducts(@PathVariable Long id) {
        Category category = categoryService.findByIdWithProducts(id);
        API_Response response = new API_Response();
        response.setMessage("Category with products retrieved successfully");
        response.setData(category);
        return ResponseEntity.ok(response);
    }
}
