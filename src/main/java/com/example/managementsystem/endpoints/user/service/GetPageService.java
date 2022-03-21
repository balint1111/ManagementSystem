package com.example.managementsystem.endpoints.user.service;

import com.example.managementsystem.entities.User;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericPageRequest;
import com.example.managementsystem.request.GetAllPageableRequest;
import com.example.managementsystem.response.GenericPageResponse;
import com.example.managementsystem.services.UserService;
import com.example.managementsystem.search.ProPageable;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("getPageUserService")
public class GetPageService {

    private final UserService userService;

    public GetPageService(UserService userService) {
        this.userService = userService;
    }

    public GenericPageResponse<User> service(GenericPageRequest<String> request, GenericPageResponse<User> response) {
        try {
            String predicate = request.getPredicate();
            GetAllPageableRequest getAllPageableRequest = new GetAllPageableRequest(new ProPageable(request.getPageable()), predicate);
            Page<User> page = userService.getAllPageable(getAllPageableRequest);

            response.setStatus(CommonStatus.OK.toString());
            response.setPage(page);
        }catch (Exception e) {
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
