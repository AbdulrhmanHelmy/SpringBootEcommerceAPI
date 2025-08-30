package com.helmy.ecommerce.Controller;

import com.helmy.ecommerce.DTO.UserDTO;
import com.helmy.ecommerce.Response.API_Response;
import com.helmy.ecommerce.Service.EmailVerificationService;
import com.helmy.ecommerce.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final EmailVerificationService verificationService;


    public AuthController(UserService userService, EmailVerificationService verificationService) {
        this.userService = userService;
        this.verificationService = verificationService;
    }

    @PostMapping("/login")
    public ResponseEntity<API_Response> login(@RequestBody UserDTO userDTO) {
        String token = userService.Login(userDTO);
        return ResponseEntity.ok(new API_Response("Success", token));
    }

    @PostMapping("/register")
    public ResponseEntity<API_Response> register(@RequestBody UserDTO userDTO) {
        userService.Register(userDTO);
        return ResponseEntity.ok(new API_Response("success", userDTO.getEmail()));
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {
        boolean verified = verificationService.verifyToken(token);
        return verified ? "Account verified successfully!" : "Invalid or expired token!";
    }
}


