package com.example.ecommerce.controller;

import com.example.ecommerce.models.User;
import com.example.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    // Kiểm tra xem người dùng đã đăng nhập chưa
    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedInUser") != null;
    }

    // Hiển thị danh sách người dùng
    @GetMapping
    public String listUsers(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/user_list"; // Tên view hiển thị danh sách người dùng
    }

    // Hiển thị form thêm người dùng mới
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        model.addAttribute("user", new User()); // Tạo đối tượng User mới để gán vào form
        return "admin/user_form"; // Tên view của form thêm người dùng
    }

    // Xử lý thêm người dùng mới
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        userService.registerUser(user); // Lưu người dùng mới vào cơ sở dữ liệu
        return "redirect:/admin/users"; // Điều hướng lại trang danh sách người dùng
    }

    // Hiển thị form sửa thông tin người dùng
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user_form"; // Tên view của form sửa người dùng
    }

    // Xử lý cập nhật thông tin người dùng
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }

        User existingUser = userService.getUserById(id); // Lấy thông tin người dùng hiện tại
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername()); // Giữ nguyên username
            existingUser.setEmail(user.getEmail()); // Cập nhật email

            // Nếu mật khẩu mới không trống, cập nhật mật khẩu
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(user.getPassword());
            }

            userService.registerUser(existingUser); // Cập nhật thông tin người dùng
        }

        return "redirect:/admin/users"; // Quay lại danh sách người dùng
    }

    // Xử lý xóa người dùng
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        userService.deleteUser(id); // Xóa người dùng
        return "redirect:/admin/users"; // Quay lại danh sách người dùng
    }
}
