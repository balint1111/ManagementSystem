package com.example.managementsystem.endpoints.user.service;

import com.example.managementsystem.entities.User;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.UserService;
import com.google.common.base.Throwables;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginService {
    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public GenericSingleResponse<User> service(GenericSingleRequest<User> request, GenericSingleResponse<User> response) {
        try {
            User user = userService.getByUsernameAndPassword(request.getParam().getUsername(), request.getParam().getPassword());
            if (user == null) {
                response.setStatus(CommonStatus.NOT_FOUND_IN_DATABASE.toString());
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
