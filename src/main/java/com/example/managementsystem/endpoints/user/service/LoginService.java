package com.example.managementsystem.endpoints.user.service;

import com.example.managementsystem.entities.User;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.UserService;
import com.google.common.base.Throwables;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public GenericSingleResponse<User> service(GenericSingleRequest<User> request, GenericSingleResponse<User> response) {
        try {
            User user = userService.getByUsername(request.getParam().getUsername());

            if (user == null || !passwordEncoder.matches(request.getParam().getPassword(), user.getPassword())) {
                response.setStatus(CommonStatus.UNAUTHORIZED.toString());
                return response;
            }
            response.setItem(user);
            response.setStatus(CommonStatus.OK.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }

        return response;
    }
}
