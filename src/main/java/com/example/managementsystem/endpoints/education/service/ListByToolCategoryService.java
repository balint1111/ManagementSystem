package com.example.managementsystem.endpoints.education.service;

import com.example.managementsystem.entities.Education;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.services.EducationService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("listEducationByToolCategoryService")
public class ListByToolCategoryService {

    private final EducationService educationService;

    public ListByToolCategoryService(EducationService educationService) {
        this.educationService = educationService;
    }

    public GenericListResponse<Education> service(GenericSingleRequest<Long> request, GenericListResponse<Education> response) {
        try {
            List<Education> list = educationService.listAllByToolCategory(request.getParam());

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
