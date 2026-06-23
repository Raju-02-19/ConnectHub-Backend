package com.chatapp.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.backend.dto.UpdateProfileRequest;
import com.chatapp.backend.model.User;
import com.chatapp.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Search User by Code
    @GetMapping("/search/{userCode}")
    public User searchUser(@PathVariable String userCode) {

        return userRepository.findByUserCode(userCode);
    }

    // Get Profile
    @GetMapping("/profile/{id}")
    public User getProfile(@PathVariable String id) {

        return userRepository.findById(id).orElse(null);
    }

    // Update Profile
    @PutMapping("/update/{id}")
    public String updateProfile(
            @PathVariable String id,
            @RequestBody UpdateProfileRequest request) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return "User Not Found";
        }

        user.setName(request.getName());
        user.setBio(request.getBio());
        user.setStatus(request.getStatus());
        user.setProfilePic(request.getProfilePic());

        userRepository.save(user);

        return "Profile Updated Successfully";
    }

    @PutMapping("/online/{userCode}")
    public String setOnline(@PathVariable String userCode) {

        User user = userRepository.findByUserCode(userCode);

        if (user == null) {
            return "User Not Found";
        }

        user.setStatus("ONLINE");

        userRepository.save(user);

        return "User is Online";
    }

    @GetMapping("/code/{userCode}")
    public User getUserByCode(
            @PathVariable String userCode) {

        return userRepository.findByUserCode(userCode);
    }

    @PutMapping("/offline/{userCode}")
    public String setOffline(@PathVariable String userCode) {

        User user = userRepository.findByUserCode(userCode);

        if (user == null) {
            return "User Not Found";
        }

        user.setStatus("OFFLINE");

        userRepository.save(user);

        return "User is Offline";
    }

    @GetMapping("/status/{userCode}")
    public String getStatus(@PathVariable String userCode) {

        User user = userRepository.findByUserCode(userCode);

        if (user == null) {
            return "User Not Found";
        }

        return user.getStatus();
    }


    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}