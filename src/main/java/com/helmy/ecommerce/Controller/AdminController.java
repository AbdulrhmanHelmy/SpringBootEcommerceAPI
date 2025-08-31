package com.helmy.ecommerce.Controller;

import com.helmy.ecommerce.Service.EmailVerificationService;
import com.helmy.ecommerce.Service.User.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
//    private final UserService userService;
    private final EmailVerificationService emailVerificationService;


    public AdminController( EmailVerificationService emailVerificationService) {
//        this.userService = userService;
        this.emailVerificationService = emailVerificationService;
    }
    @GetMapping("/deleteTokens")
    public String deleteExpiredTokens(){
        emailVerificationService.deleteExpiredTokens();
        return "deleted Successfully";
    }
    
}
