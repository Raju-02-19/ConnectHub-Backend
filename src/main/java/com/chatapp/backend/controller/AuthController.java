package com.chatapp.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.backend.dto.LoginRequest;
import com.chatapp.backend.dto.RegisterRequest;
import com.chatapp.backend.model.User;
import com.chatapp.backend.repository.UserRepository;
import com.chatapp.backend.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        String email = request.getEmail();

        User existingUser =
                userRepository.findByEmail(email);

        if (existingUser != null) {
            return "Email already exists";
        }

        User user = new User();

        user.setName(
                request.getName());

        user.setEmail(email);

        user.setPassword(
                request.getPassword());

        user.setStatus(
                "OFFLINE");

        long count =
                userRepository.count();

        String userCode =
                "CH" + (1001 + count);

        user.setUserCode(
                userCode);

        userRepository.save(user);

        return "User Registered Successfully. Your User Code is: "
                + userCode;
    }

    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestBody LoginRequest request) {

        String email = request.getEmail();

        User user =
                userRepository.findByEmail(email);

        if (user == null) {

            Map<String, Object> error =
                    new HashMap<>();

            error.put(
                    "message",
                    "User Not Found");

            return error;
        }

        if (!user.getPassword()
                .equals(
                        request.getPassword())) {

            Map<String, Object> error =
                    new HashMap<>();

            error.put(
                    "message",
                    "Invalid Password");

            return error;
        }

        // Set User Online
        user.setStatus(
                "ONLINE");

        userRepository.save(
                user);

        // Generate JWT Token
        String token =
                jwtUtil.generateToken(
                        user.getEmail());

        Map<String, Object> response =
                new HashMap<>(6);

        response.put(
                "message",
                "Login Successful");

        response.put(
                "token",
                token);

        response.put(
                "id",
                user.getId());

        response.put(
                "userCode",
                user.getUserCode());

        response.put(
                "name",
                user.getName());

        response.put(
                "email",
                user.getEmail());

        response.put(
                "status",
                user.getStatus());

        return response;
    }
}
