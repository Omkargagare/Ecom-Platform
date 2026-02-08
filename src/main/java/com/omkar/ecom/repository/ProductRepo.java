package com.omkar.ecom.repository;

import com.omkar.ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :KEYWORD,'%') ) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :KEYWORD,'%') ) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :KEYWORD,'%') ) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :KEYWORD,'%') ) " )
    List<Product> searchProducts(@Param("KEYWORD") String keyword);
}
