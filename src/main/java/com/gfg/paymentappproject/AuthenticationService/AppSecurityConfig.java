package com.gfg.paymentappproject.AuthenticationService;

import com.gfg.paymentappproject.UserService.MyPasswordEncoder;
import com.gfg.paymentappproject.UserService.manager.UserService;
import com.gfg.paymentappproject.UserService.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserAuthnService userAuthnService;
    @Autowired
    private MyPasswordEncoder passWordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userAuthnService)
                .passwordEncoder(passWordEncoder)
                .and()
                .inMemoryAuthentication()
                .passwordEncoder(passWordEncoder)
                .withUser("admin")
                .authorities("ADMIN")
                .password(passWordEncoder.encode("password"));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/user/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/transaction/**")
                .authenticated()
                .and()
                .csrf()
                .disable();
    }
}
