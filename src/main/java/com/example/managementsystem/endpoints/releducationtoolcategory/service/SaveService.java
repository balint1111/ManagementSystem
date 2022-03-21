package com.example.managementsystem.endpoints.releducationtoolcategory.service;

import com.example.managementsystem.entities.RelEducationToolCategory;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.RelEducationToolCategoryService;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Service;

@Service("relEducationToolCategorySaveService")
public class SaveService {
    private final RelEducationToolCategoryService relEducationToolCategoryService;

    public SaveService(RelEducationToolCategoryService relEducationToolCategoryService) {
        this.relEducationToolCategoryService = relEducationToolCategoryService;
    }

    public GenericSingleResponse<RelEducationToolCategory> service(GenericSingleRequest<RelEducationToolCategory> request, GenericSingleResponse<RelEducationToolCategory> response) {
        try {
            RelEducationToolCategory relEducationToolCategory = relEducationToolCategoryService.save(request.getParam());
            response.setItem(relEducationToolCategory);
            response.setStatus(CommonStatus.OK.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
