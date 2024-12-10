package com.example.ecommerce.controller;

import com.example.ecommerce.models.AdminUser;
import com.example.ecommerce.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AdminUserService adminUserService;

    // Hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("adminUser", new AdminUser());
        return "auth/register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute AdminUser adminUser, Model model) {
        adminUserService.registerAdmin(adminUser);
        model.addAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "auth/login";
    }

    // Hiển thị form đăng nhập
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("adminUser", new AdminUser());
        return "auth/login";
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String loginAdmin(@ModelAttribute AdminUser adminUser, Model model) {
        AdminUser foundAdmin = adminUserService.findByUsername(adminUser.getUsername())
                .filter(user -> user.getPassword().equals(adminUser.getPassword()))
                .orElse(null);

        if (foundAdmin != null) {
            return "redirect:/admin"; // Điều hướng đến trang quản trị
        } else {
            model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "auth/login";
        }
    }
}
