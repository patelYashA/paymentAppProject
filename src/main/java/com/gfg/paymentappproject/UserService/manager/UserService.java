package com.gfg.paymentappproject.UserService.manager;

import com.gfg.paymentappproject.UserService.entities.UserResponse;
import com.gfg.paymentappproject.UserService.entities.UserServiceRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    void createUser(UserServiceRequest userServiceRequest);
    UserResponse get(String username);
}
