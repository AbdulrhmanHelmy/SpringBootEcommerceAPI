package com.helmy.ecommerce.Controller;

import com.helmy.ecommerce.Response.API_Response;
import com.helmy.ecommerce.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<API_Response> get(){
        API_Response apiResponse = new API_Response("User Founded Successfully",
                userService.getCurrentUser());
        return ResponseEntity.ok(apiResponse);
    }
}
