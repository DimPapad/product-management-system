package com.example.productmanagementsystem.security;

import com.example.productmanagementsystem.security.services.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig (MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService=myUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, MyUserDetailsService myUserDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(myUserDetailService)
                .passwordEncoder(getPasswordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http
               .csrf().disable()
               .authorizeHttpRequests((authorize)->authorize
                       .requestMatchers(HttpMethod.GET).hasRole("ADMIN")
                       .anyRequest().authenticated())
               .httpBasic()
               .and()
               .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
