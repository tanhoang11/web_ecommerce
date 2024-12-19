package com.example.ecommerce.controller.client;

import com.example.ecommerce.models.User;
import com.example.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        userService.registerUser(user);
        model.addAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "user/login";
    }

    // Hiển thị form đăng nhập
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model ,HttpSession session) {
        User foundUser = userService.findByUsername(user.getUsername())
                .filter(u -> u.getPassword().equals(user.getPassword()))
                .orElse(null);

        if (foundUser != null) {
            session.setAttribute("loggedInUser", foundUser);// Lưu thông tin người dùng vào session

            return "redirect:/"; // Điều hướng đến trang chính
        } else {
            model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "user/login";
        }
    }
    // Xử lý đăng xuất
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate(); // Hủy session, người dùng sẽ bị đăng xuất
        return "redirect:/user/login"; // Quay lại trang đăng nhập
    }

    // Hiển thị trang hồ sơ người dùng
    @GetMapping("/profile")
    public String showProfilePage(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("user", loggedInUser); // Thêm thông tin người dùng vào model
            return "user/profile"; // Trang hồ sơ người dùng
        } else {
            return "redirect:/user/login"; // Nếu chưa đăng nhập, chuyển về trang đăng nhập
        }
    }

    // Xử lý chỉnh sửa hồ sơ
    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute User user, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            loggedInUser.setFullName(user.getFullName());
            loggedInUser.setBirthDate(user.getBirthDate());
            loggedInUser.setPhoneNumber(user.getPhoneNumber());
            loggedInUser.setAddress(user.getAddress());
            userService.save(loggedInUser); // Lưu thông tin đã chỉnh sửa
            model.addAttribute("user", loggedInUser);
            return "user/profile"; // Quay lại trang hồ sơ sau khi cập nhật
        }
        return "redirect:/user/login";
    }

}
