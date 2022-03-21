package com.example.managementsystem.endpoints.releducationtoolcategory.service;

import com.example.managementsystem.entities.RelEducationToolCategory;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.services.RelEducationToolCategoryService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("listRelEducationToolCategoryService")
public class ListService {

    private final RelEducationToolCategoryService relEducationToolCategoryService;

    public ListService(RelEducationToolCategoryService relEducationToolCategoryService) {
        this.relEducationToolCategoryService = relEducationToolCategoryService;
    }

    public GenericListResponse<RelEducationToolCategory> service(GenericSingleRequest<String> request, GenericListResponse<RelEducationToolCategory> response) {
        try {
            String predicate = request.getParam();
            List<RelEducationToolCategory> list = relEducationToolCategoryService.getAll(predicate);

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
