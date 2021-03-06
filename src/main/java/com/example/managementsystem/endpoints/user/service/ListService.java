package com.example.managementsystem.endpoints.user.service;

import com.example.managementsystem.entities.User;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.services.UserService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("listUserService")
public class ListService {

    private final UserService userService;

    public ListService(UserService userService) {
        this.userService = userService;
    }

    public GenericListResponse<User> service(GenericSingleRequest<String> request, GenericListResponse<User> response) {
        try {
            String predicate = request.getParam();
            List<User> list = userService.getAll(predicate);

            response.setStatus(CommonStatus.OK.toString());
            response.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
