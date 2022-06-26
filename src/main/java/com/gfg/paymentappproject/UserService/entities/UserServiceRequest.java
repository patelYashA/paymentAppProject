package com.gfg.paymentappproject.UserService.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserServiceRequest {
    private String username;
    private String password;
    private String email;
}
