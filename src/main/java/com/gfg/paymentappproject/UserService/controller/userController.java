package com.gfg.paymentappproject.UserService.controller;

import com.gfg.paymentappproject.UserService.entities.UserServiceRequest;
import com.gfg.paymentappproject.UserService.manager.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class userController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    void create(@RequestBody UserServiceRequest userServiceRequest){
        userService.createUser(userServiceRequest);
    }

    @GetMapping("/user/{username}")
    ResponseEntity get(@PathVariable("username") String username){
        return ResponseEntity.ok(userService.get(username));
    }
}
