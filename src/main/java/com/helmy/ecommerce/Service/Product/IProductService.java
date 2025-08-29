package com.helmy.ecommerce.Service.Product;

import com.helmy.ecommerce.DTO.ImageDto;
import com.helmy.ecommerce.DTO.ProductDto;
import com.helmy.ecommerce.Model.Product;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface IProductService {
    Product createProduct(ProductDto productDto) throws IOException;
    Product updateProduct(Long id, ProductDto productDto) throws IOException;
    void deleteProduct( Long id) throws IOException;
    Product getProductById(Long id);
    Page<Product> getProductsByCategory(String CategoryName,int page, int size);

    Page<Product> getProducts(int page, int size);

    void deleteImageFromProduct(Long productId, Long imageId) throws IOException;
    public Product addImageToProduct(Long productId, ImageDto dto) throws IOException ;

    }
