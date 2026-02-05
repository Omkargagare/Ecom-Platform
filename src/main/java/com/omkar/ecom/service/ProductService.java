package com.omkar.ecom.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.omkar.ecom.model.Product;
import com.omkar.ecom.repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    private final ProductRepo repo;

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    public List<Product> findAllProducts() {
        return repo.findAll();
    }

    public void saveProduct(Product prod) {
        repo.save(prod);
    }

    public void deleteProduct(Integer prodId) {
        repo.deleteById(prodId);
    }

    public Product findProductById(Integer prodId) {
        return repo.findById(prodId).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }
}
