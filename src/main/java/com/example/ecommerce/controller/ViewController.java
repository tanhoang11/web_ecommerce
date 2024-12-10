package com.example.ecommerce.controller;

import com.example.ecommerce.models.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ViewController {
    @Autowired
    private ProductService productService;
//
    @GetMapping("/")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "FoodMart-1.0.0/index";
    }
    @GetMapping("/admin")
    public String showAdminDashboard() {
        return "admin/admin_dashboard"; // Tên view của trang dashboard
    }
}