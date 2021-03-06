package com.example.managementsystem.endpoints.education.service;

import com.example.managementsystem.entities.Education;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.EducationService;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Service;

@Service("educationSaveService")
public class SaveService {
    private final EducationService educationService;

    public SaveService(EducationService educationService) {
        this.educationService = educationService;
    }

    public GenericSingleResponse<Education> service(GenericSingleRequest<Education> request, GenericSingleResponse<Education> response) {
        try {
            Education education = educationService.save(request.getParam());
            response.setItem(education);
            response.setStatus(CommonStatus.OK.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
