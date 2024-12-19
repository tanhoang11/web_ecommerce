package com.example.ecommerce.controller.admin;

import com.example.ecommerce.models.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/products") // Đảm bảo đường dẫn chính xác là "/admin/products"
public class AdminController {

    @Autowired
    private ProductService productService;

    // Kiểm tra người dùng đã đăng nhập chưa
    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedInUser") != null;
    }

    // Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProducts(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products"; // Tên view trả về
    }

    // Hiển thị form tạo mới sản phẩm
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        model.addAttribute("product", new Product());
        return "admin/product_form"; // Tên view của form tạo mới
    }

    // Xử lý tạo mới sản phẩm
    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        productService.createProduct(product);
        return "redirect:/admin/products"; // Đảm bảo điều hướng đúng đến trang danh sách sản phẩm
    }

    // Hiển thị form sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product_form"; // Tên view của form sửa sản phẩm
    }

    // Xử lý cập nhật sản phẩm
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        productService.updateProduct(id, product);
        return "redirect:/admin/products"; // Đảm bảo điều hướng đúng đến trang danh sách sản phẩm
    }

    // Xử lý xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        productService.deleteProduct(id);
        return "redirect:/admin/products"; // Điều hướng lại đến trang danh sách sản phẩm
    }
}
