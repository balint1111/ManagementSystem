package com.example.managementsystem.endpoints.education.service;

import com.example.managementsystem.entities.Education;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.services.EducationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("listEducationService")
public class ListService {

    private final EducationService educationService;

    public ListService(EducationService educationService) {
        this.educationService = educationService;
    }

    public GenericListResponse<Education> service(GenericSingleRequest<String> request, GenericListResponse<Education> response) {

        String predicate = request.getParam();
        List<Education> list = educationService.getAll(predicate);

        response.setStatus(CommonStatus.OK.toString());
        response.setItems(list);

        return response;
    }
}
