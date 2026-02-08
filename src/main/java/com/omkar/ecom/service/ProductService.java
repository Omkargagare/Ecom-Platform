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

    public Product updateProduct(Integer prodId, Product prod, MultipartFile imageFile) throws IOException {
        Optional<Product> product = repo.findById(prodId);
        if (product.isPresent()) {
            prod.setImageData(imageFile.getBytes());
            prod.setImageName(imageFile.getOriginalFilename());
            prod.setImageType(imageFile.getContentType());
            return repo.save(prod);
        } else {
            return null;
        }
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
