package com.gfg.paymentappproject.UserService.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.paymentappproject.UserService.MyPasswordEncoder;
import com.gfg.paymentappproject.UserService.entities.User;
import com.gfg.paymentappproject.UserService.entities.UserCreatedResponse;
import com.gfg.paymentappproject.UserService.entities.UserResponse;
import com.gfg.paymentappproject.UserService.entities.UserServiceRequest;
import com.gfg.paymentappproject.UserService.repo.UserRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Qualifier("service1")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper = new ObjectMapper();
    @SneakyThrows
    @Override
    public void createUser(UserServiceRequest userServiceRequest) {
        User user = User.builder()
                .username(userServiceRequest.getUsername())
                .password(myPasswordEncoder.getPasswordEncoder().encode(userServiceRequest.getPassword()))
                .email(userServiceRequest.getEmail())
                .build();
        UserCreatedResponse userCreatedResponse = UserCreatedResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
        userRepo.save(user);
        kafkaTemplate.send("userTopic", objectMapper.writeValueAsString(userCreatedResponse));
    }

    @Override
    public UserResponse get(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        return userResponse;
    }
}
