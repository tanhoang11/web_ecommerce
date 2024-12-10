package com.example.ecommerce.controller;

import com.example.ecommerce.models.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products") // Đảm bảo đường dẫn chính xác là "/admin/products"
public class AdminController {

    @Autowired
    private ProductService productService;

    // Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products"; // Tên view trả về
    }

    // Hiển thị form tạo mới sản phẩm
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product_form"; // Tên view của form tạo mới
    }

    // Xử lý tạo mới sản phẩm
    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product) {
        productService.createProduct(product);
        return "redirect:/admin/products"; // Đảm bảo điều hướng đúng đến trang danh sách sản phẩm
    }

    // Hiển thị form sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product_form"; // Tên view của form sửa sản phẩm
    }

    // Xử lý cập nhật sản phẩm
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        productService.updateProduct(id, product);
        return "redirect:/admin/products"; // Đảm bảo điều hướng đúng đến trang danh sách sản phẩm
    }

    // Xử lý xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products"; // Điều hướng lại đến trang danh sách sản phẩm
    }
    // Hiển thị trang quản trị

}
