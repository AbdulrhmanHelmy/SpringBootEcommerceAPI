package com.helmy.ecommerce.Controller;

import com.helmy.ecommerce.DTO.ImageDto;
import com.helmy.ecommerce.DTO.ProductDto;
import com.helmy.ecommerce.Response.API_Response;
import com.helmy.ecommerce.Service.Product.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }


    @GetMapping
    public ResponseEntity<API_Response> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("success");
        apiResponse.setData(productService.getProducts(page, size).getContent());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/category")
    public ResponseEntity<API_Response> getAllByCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam String categoryName
    ) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("success");
        apiResponse.setData(productService.getProductsByCategory(categoryName, page, size).getContent());
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<API_Response> addProductData(@RequestBody ProductDto productDto) {
        API_Response apiResponse = new API_Response();
        apiResponse.setData(productService.createProduct(productDto));
        apiResponse.setMessage("Added Product Data");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/img/{id}")
    public ResponseEntity<API_Response> addProductIMG(@PathVariable Long id, @RequestParam("image") MultipartFile file) throws IOException {
        API_Response apiResponse = new API_Response();
        ImageDto imageDto = new ImageDto(file);
        apiResponse.setData(productService.addImageToProduct(id, imageDto));
        apiResponse.setMessage("added successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<API_Response> deleteProduct(@PathVariable Long id) throws IOException {
        API_Response apiResponse = new API_Response();
        productService.deleteProduct(id);
        apiResponse.setData(null);
        apiResponse.setMessage(" Product Deleted Successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/img/{id}/{imgId}")
    public ResponseEntity<API_Response> deleteImage(@PathVariable Long id, @PathVariable Long imgId ) throws IOException {
        API_Response apiResponse = new API_Response();
        productService.deleteImageFromProduct(id,imgId);
        apiResponse.setData(null);
        apiResponse.setMessage(" Image Deleted Successfully");
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<API_Response> getProductById(@PathVariable Long id) {
        API_Response apiResponse = new API_Response();
        apiResponse.setData(productService.getProductById(id));
        apiResponse.setMessage("success");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<API_Response> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) throws IOException {
        API_Response apiResponse = new API_Response();
        apiResponse.setData(productService.updateProduct(id, productDto));
        apiResponse.setMessage("Product Updated Successfully");
        return ResponseEntity.ok(apiResponse);
    }



}
