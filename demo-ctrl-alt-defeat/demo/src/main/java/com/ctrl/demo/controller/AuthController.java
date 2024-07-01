package com.ctrl.demo.controller;

import com.ctrl.demo.model.User;
import com.ctrl.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> registrationRequest) {
        User user = new User();
        user.setUsername(registrationRequest.get("username"));
        user.setEmail(registrationRequest.get("email"));
        user.setPassword(passwordEncoder.encode(registrationRequest.get("password")));
        user.setRole(registrationRequest.get("role")); // USER or MERCHANT

        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginRequest) {
        String identifier = loginRequest.get("identifier"); // username or email
        String password = loginRequest.get("password");

        Optional<User> user = userService.authenticate(identifier, password);

        if (user.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.get().getId());
            response.put("username", user.get().getUsername());
            response.put("email", user.get().getEmail());
            response.put("role", user.get().getRole());
            return response;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @PutMapping("/update-password")
    public User updatePassword(@RequestBody Map<String, String> updateRequest) {
        User user = userService.findByEmail(updateRequest.get("email"));
        if (user != null) {
            user.setPassword(passwordEncoder.encode(updateRequest.get("newPassword")));
            return userService.saveUser(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setRole(userDetails.getRole());
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }
            // else set the password to existing users password
            else {
                existingUser.setPassword(existingUser.getPassword());
            }
            return userService.saveUser(existingUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/users/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }
}
