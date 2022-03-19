package com.example.managementsystem.endpoints.education.service;

import com.example.managementsystem.entities.User;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.UserService;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Service;

@Service("educationSaveService")
public class SaveService {
    private final UserService userService;

    public SaveService(UserService userService) {
        this.userService = userService;
    }

    public GenericSingleResponse<User> service(GenericSingleRequest<User> request, GenericSingleResponse<User> response){
        try {
            User user = userService.save(request.getParam());
            response.setItem(user);
            response.setStatus(CommonStatus.OK.toString());
        }catch (Exception e) {
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
