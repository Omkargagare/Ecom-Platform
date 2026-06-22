package com.omkar.ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String brand;
    private String category;
    private BigDecimal price;
    private boolean productAvailable;
    private int stockQuantity;
    private Date releaseDate;

    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;

}
