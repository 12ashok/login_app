package com.example.login.controller;

import com.example.login.dto.LoginRequest;
import com.example.login.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // returns login.html
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // returns home.html
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody User user) {
        // For demo: just echo back
        return "User registered: " + user.getUsername();
    }
}

