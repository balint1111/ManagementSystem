package com.example.managementsystem.services;

import com.example.managementsystem.entities.User;
import com.example.managementsystem.enumeration.UserType;
import com.example.managementsystem.system.IAuthenticationFacade;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final IAuthenticationFacade authenticationFacade;
    private final UserService userService;

    public AuthService(IAuthenticationFacade authenticationFacade, UserService userService) {
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
    }


    public User getCurrentUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication != null ? userService.getByUsername(authentication.getName()) : null;
    }

    public boolean hasAnyAuthorities(UserType... authorities) {
        if (authorities.length == 0) {
            return true;
        }
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication == null) {
            return false;
        }
        List<String> userAuthorities = authentication.getAuthorities().stream().map(GrantedAuthority::toString).collect(Collectors.toList());
        for (UserType authority : authorities) {
            if (userAuthorities.contains(authority.toString())) {
                return true;
            }
        }
        return false;
    }
}
