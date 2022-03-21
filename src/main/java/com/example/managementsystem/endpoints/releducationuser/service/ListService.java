package com.example.managementsystem.endpoints.releducationuser.service;

import com.example.managementsystem.entities.RelEducationToolCategory;
import com.example.managementsystem.entities.RelEducationUser;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.services.RelEducationToolCategoryService;
import com.example.managementsystem.services.RelEducationUserService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("listRelEducationUserService")
public class ListService {

    private final RelEducationUserService relEducationUserService;

    public ListService(RelEducationUserService relEducationUserService) {
        this.relEducationUserService = relEducationUserService;
    }

    public GenericListResponse<RelEducationUser> service(GenericSingleRequest<String> request, GenericListResponse<RelEducationUser> response) {
        try {
            String predicate = request.getParam();
            List<RelEducationUser> list = relEducationUserService.getAll(predicate);

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
