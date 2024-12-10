package com.example.ecommerce.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    @Column(nullable = false)
    private String product_name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    

    private Integer category_id;

    private String image_url;



    // Getters and Setters
    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    public Product() {
    }
    public Product(String product_name, String description, BigDecimal price, Integer category_id, String image_url, Timestamp created_at, Timestamp updated_at) {
        this.product_name = product_name;
        this.description = description;
        this.price = price;
        this.category_id = category_id;
        this.image_url = image_url;
        
    }
}
