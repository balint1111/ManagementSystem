package com.example.managementsystem.endpoints.toolcategory.service;

import com.example.managementsystem.entities.ToolCategory;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.ToolCategoryService;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Service;

@Service("toolCategorySaveService")
public class SaveService {
    private final ToolCategoryService toolCategoryService;

    public SaveService(ToolCategoryService toolCategoryService) {
        this.toolCategoryService = toolCategoryService;
    }

    public GenericSingleResponse<ToolCategory> service(GenericSingleRequest<ToolCategory> request, GenericSingleResponse<ToolCategory> response) {
        try {
            ToolCategory ToolCategory = toolCategoryService.save(request.getParam());
            response.setItem(ToolCategory);
            response.setStatus(CommonStatus.OK.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
