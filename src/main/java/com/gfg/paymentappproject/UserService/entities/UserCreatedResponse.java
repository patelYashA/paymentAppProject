package com.gfg.paymentappproject.UserService.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreatedResponse {
    private String username;
    private String email;
}
