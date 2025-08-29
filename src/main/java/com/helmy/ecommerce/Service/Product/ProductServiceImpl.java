package com.helmy.ecommerce.Service.Product;

import com.helmy.ecommerce.DTO.ImageDto;
import com.helmy.ecommerce.DTO.ProductDto;
import com.helmy.ecommerce.Exeption.ResourceNotFound;
import com.helmy.ecommerce.Model.Category;
import com.helmy.ecommerce.Model.Image;
import com.helmy.ecommerce.Model.Product;
import com.helmy.ecommerce.Repository.CategoryRepository;
import com.helmy.ecommerce.Repository.ProductRepository;
import com.helmy.ecommerce.Service.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public Product createProduct(ProductDto productDto){
        Product p = new Product();
        setData(p, productDto);
        return productRepository.save(p);
    }

    @Transactional
    @Override
    public Product addImageToProduct(Long productId, ImageDto dto) throws IOException {
        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product not found: " + productId));

        if (p.getImages() == null) {
            p.setImages(new ArrayList<>());
        }

        Image img = convertToImage(dto);
        img.setProduct(p);
        p.getImages().add(img);

        return productRepository.save(p);
    }
    @Override
    @Transactional
    public void deleteImageFromProduct(Long productId, Long imageId) throws IOException{
        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product not found: " + productId));

        Image image = p.getImages().stream()
                .filter(img -> img.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound("Image not found: " + imageId));

        p.getImages().remove(image);
        imageService.delete(imageId);
        productRepository.save(p);
    }


    private Image convertToImage(ImageDto imageDto) throws IOException {
        return imageService.upload(imageDto.getMultipartFile());
    }

    private void setData(Product p, ProductDto productDto) {
        p.setName(productDto.getName());
        p.setDescription(productDto.getDescription());
        p.setCurrency(productDto.getCurrency());
        p.setPrice(productDto.getPrice());
        p.setStock(productDto.getStock());
        p.setIsAvailable(productDto.getIsAvailable());
        Category c = categoryRepository.findByNameIgnoreCase(productDto.getCategoryDto().getName());
        if (c == null) {
            throw new ResourceNotFound("Category not found: " + productDto.getCategoryDto().getName());
        }
        p.setCategory(c);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, ProductDto productDto) throws IOException {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found"));
        setData(existing, productDto);
        return productRepository.save(existing);
    }

    @Override
    @Transactional

    public void deleteProduct(Long id) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found"));

        for (Image i : product.getImages()) {
            imageService.delete(i.getId());
        }
        productRepository.delete(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found"));
    }

    @Override
    public Page<Product> getProductsByCategory(String categoryName, int page, int size) {
        return productRepository.findByCategory_Name(categoryName, PageRequest.of(page, size));
    }

    @Override
    public Page<Product> getProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }


}
