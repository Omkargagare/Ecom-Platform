package com.omkar.ecom.controller;

import java.io.IOException;
import java.util.List;

import com.omkar.ecom.model.Product;
import com.omkar.ecom.response.ApiResponse;
import com.omkar.ecom.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<Product>>> findAllProducts() {
        List<Product> products = service.findAllProducts();
        return ResponseEntity.ok(new ApiResponse<>("Products retrieved successfully", products, true));
    }

    @GetMapping("/product/{prodId}")
    public ResponseEntity<ApiResponse<Product>> findProductById(@PathVariable Integer prodId) {
        Product product = service.findProductById(prodId);
        return ResponseEntity.ok(new ApiResponse<>("Product retrieved successfully", product, true));
    }

    @GetMapping("/product/{prodId}/image")
    public ResponseEntity<byte[]> getImageById(@PathVariable Integer prodId) {
        Product product = service.findProductById(prodId);

        if (product.getImageData() == null || product.getImageType() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(product.getImageData());
    }

    @GetMapping("/products/search")
    public ResponseEntity<ApiResponse<List<Product>>> searchProducts(@RequestParam String keyword) {
        List<Product> products = service.searchProducts(keyword);
        return ResponseEntity.ok(new ApiResponse<>("Search results retrieved", products, true));
    }

    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestPart("product") Product product, @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        Product savedProduct = service.addProduct(product, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Product created successfully", savedProduct, true));
    }

    @PutMapping(value = "/product/{prodId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Integer prodId, @RequestPart("product") Product product, @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        Product updatedProduct = service.updateProduct(prodId, product, imageFile);
        return ResponseEntity.ok(new ApiResponse<>("Product updated successfully", updatedProduct, true));
    }

    @DeleteMapping("/product/{prodId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Integer prodId) {
        service.deleteProduct(prodId);
        return ResponseEntity.ok(new ApiResponse<>("Product deleted successfully", null, true));
    }
}
