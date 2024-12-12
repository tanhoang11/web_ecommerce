package com.example.ecommerce.service;

import com.example.ecommerce.models.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Lấy tất cả người dùng
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Lấy thông tin người dùng theo ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Xóa người dùng
    public void deleteUser(Long id) {
        userRepository.deleteById(id); // Xóa người dùng khỏi cơ sở dữ liệu
    }
}
