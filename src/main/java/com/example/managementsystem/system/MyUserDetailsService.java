package com.example.managementsystem.system;

import com.example.managementsystem.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public MyUserDetailsService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) {
        com.example.managementsystem.entities.User dbUser = userService.getByUsername(username);
        log.info("authenticating: " + username);
        return new User(dbUser.getUsername(), dbUser.getPassword(), Arrays.asList(new SimpleGrantedAuthority(dbUser.getUserType().toString())));
    }
}