package com.example.ecommerce.controller;

import com.example.ecommerce.models.AdminUser;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import jakarta.servlet.http.HttpSession;

@Controller
public class ViewController {
    @Autowired
    private ProductService productService;
//
    @GetMapping("/")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "/index";
    }
    @GetMapping("/admin")
    public String showAdminDashboard(HttpSession session) {
        AdminUser loggedInUser = (AdminUser) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            return "redirect:/auth/login";
        }

        // Nếu đã đăng nhập, hiển thị trang admin dashboard
        return "admin/admin_dashboard";
    }
}