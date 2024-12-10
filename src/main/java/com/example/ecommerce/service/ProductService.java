package com.example.ecommerce.service;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ecommerce.exception.ProductNotFoundException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    //lấy all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();

    }
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
    }
//    public Product getProductById(Integer productId) {
//        return productRepository.findById(productId).orElse(null);
//    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product productDetails) {
        Product product = getProductById(productId);
        product.setProduct_name(productDetails.getProduct_name());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setCategory_id(productDetails.getCategory_id());
        product.setImage_url(productDetails.getImage_url());
        return productRepository.save(product);
    }


    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}