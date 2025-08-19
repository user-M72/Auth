package com.example.Auth2.service;

import com.example.Auth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrEmail(login)
                .map(u ->  org.springframework.security.core.userdetails.User
                        .withUsername(u.getEmail())
                        .password(u.getPassword() != null ? u.getPassword() : "")
                        .authorities("USER")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + login));
    }
}
