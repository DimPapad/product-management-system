package com.example.productmanagementsystem.security.services;

import com.example.productmanagementsystem.models.User;
import com.example.productmanagementsystem.repositories.UserRepository;
import com.example.productmanagementsystem.security.models.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User currentUser=userRepository.findByUsername(username);
        if (currentUser==null) {
            throw new UsernameNotFoundException("Er.404 - User " + username + " not found");
        }
        return new MyUserDetails(currentUser);
    }
}
