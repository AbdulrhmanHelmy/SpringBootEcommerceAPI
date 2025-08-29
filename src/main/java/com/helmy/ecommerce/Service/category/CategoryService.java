package com.helmy.ecommerce.Service.category;

import com.helmy.ecommerce.DTO.CategoryDto;
import com.helmy.ecommerce.Exeption.ResourceNotFound;
import com.helmy.ecommerce.Model.Category;
import com.helmy.ecommerce.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    @Transactional
    public Category create(CategoryDto categoryDto) {
        Category c=new Category();
        c.setName(categoryDto.getName());
        c.setDescription(categoryDto.getDescription());
        return categoryRepository.save(c);
    }

    @Override
    @Transactional

    public Category update(Long id, CategoryDto categoryDto) {
        //get and update

        Category c= findById(id);
        c.setDescription(categoryDto.getDescription());
        c.setName(categoryDto.getName());

        return categoryRepository.save(c);
    }

    @Override
    @Transactional

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Category id not found: " + id));
    }





    @Override
    public Category findByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

    @Override
    public Category findByIdWithProducts(Long id) {
        return categoryRepository.findByIdWithProducts(id).orElseThrow(()->new ResourceNotFound("Category Not Found"));
    }


}
