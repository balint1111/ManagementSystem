package com.example.managementsystem.endpoints.user.service;

import com.example.managementsystem.entities.User;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.UserService;
import com.google.common.base.Throwables;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userSaveService")
public class SaveService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SaveService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public GenericSingleResponse<User> service(GenericSingleRequest<User> request, GenericSingleResponse<User> response) {
        try {
            User userWithSameUsername = userService.getByUsername(request.getParam().getUsername());
            if (userWithSameUsername != null && userWithSameUsername.getId() != null) {
                response.setStatus("USER_EXIST_WITH_THIS_USERNAME");
                return response;
            }
            request.getParam().setPassword(passwordEncoder.encode(request.getParam().getPassword()));
            User user = userService.save(request.getParam());
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
