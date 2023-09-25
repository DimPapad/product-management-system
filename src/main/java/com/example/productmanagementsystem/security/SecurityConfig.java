package com.example.productmanagementsystem.security;

import com.example.productmanagementsystem.security.services.MyUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig (MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService=myUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(myUserDetailsService)
            .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/swagger-ui/*").permitAll()
            .antMatchers(HttpMethod.POST, "/user/register").permitAll()
            .antMatchers(HttpMethod.GET, "/user/login").permitAll()
            .antMatchers(HttpMethod.GET, "/user/audit").hasAnyRole("USER","ADMIN")
            .antMatchers(HttpMethod.GET, "/user/home").hasAnyRole("USER","ADMIN")
            .antMatchers(HttpMethod.PUT, "/user/changerole").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/product/all").hasAnyRole("USER","ADMIN")
            .antMatchers(HttpMethod.GET, "/product/name").hasAnyRole("USER","ADMIN")
            .antMatchers(HttpMethod.POST, "/product/add").hasAnyRole("USER","ADMIN")
            .antMatchers(HttpMethod.GET, "/product/audit").hasAnyRole("USER","ADMIN")
            .antMatchers(HttpMethod.PUT, "/product/edit").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/product/delete/*").hasRole("ADMIN")
            .anyRequest().fullyAuthenticated()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
