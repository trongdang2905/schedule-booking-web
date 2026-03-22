package com.example.schedulebookingweb.controller;

import com.example.schedulebookingweb.model.User;
import com.example.schedulebookingweb.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showUser(Authentication authentication) {
        // Nếu đã đăng nhập → redirect thẳng đến dashboard
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/booking-schedule";
        }
        return "index"; // Chưa đăng nhập → hiện trang login
    }

    @GetMapping("/booking-schedule")
    public String showBookingSchedule(Authentication authentication, Model model) {
        // Lấy email từ Security context (username = email)
        String email = authentication.getName();
	System.out.println("Email:" + email);
        User user = userService.findByEmail(email);

        // Inject từng field cần thiết để tránh LazyInitializationException
        // khi Thymeleaf serialize cả User entity (có LAZY collection)
        model.addAttribute("currentUserId", user.getId());
        model.addAttribute("currentUserFullName", user.getFullName());
        model.addAttribute("currentUserEmail", user.getEmail());
        model.addAttribute("currentUserRoleName",
                user.getRole() != null ? user.getRole().getName() : "USER");
        model.addAttribute("currentUserRoleId",
                user.getRole() != null ? user.getRole().getId() : 0);

        return "dashboard";
    }
}
