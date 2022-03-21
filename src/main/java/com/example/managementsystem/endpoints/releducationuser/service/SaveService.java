package com.example.managementsystem.endpoints.releducationuser.service;

import com.example.managementsystem.entities.RelEducationToolCategory;
import com.example.managementsystem.entities.RelEducationUser;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.RelEducationToolCategoryService;
import com.example.managementsystem.services.RelEducationUserService;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Service;

@Service("relEducationUserSaveService")
public class SaveService {
    private final RelEducationUserService relEducationUserService;

    public SaveService(RelEducationUserService relEducationUserService) {
        this.relEducationUserService = relEducationUserService;
    }

    public GenericSingleResponse<RelEducationUser> service(GenericSingleRequest<RelEducationUser> request, GenericSingleResponse<RelEducationUser> response) {
        try {
            RelEducationUser relEducationUser = relEducationUserService.save(request.getParam());
            response.setItem(relEducationUser);
            response.setStatus(CommonStatus.OK.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
