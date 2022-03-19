package com.example.managementsystem.endpoints.education.service;

import com.example.managementsystem.entities.Education;
import com.example.managementsystem.entities.User;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.EducationService;
import org.springframework.stereotype.Service;

@Service("educationGetByIdService")
public class GetByIdService {
    private final EducationService educationService;

    public GetByIdService(EducationService educationService) {
        this.educationService = educationService;
    }

    public GenericSingleResponse<Education> service(GenericSingleRequest<Long> request, GenericSingleResponse<Education> response){
        Education education = educationService.getById(request.getParam());
        response.setItem(education);
        response.setStatus(CommonStatus.OK.toString());
        return response;
    }
}
