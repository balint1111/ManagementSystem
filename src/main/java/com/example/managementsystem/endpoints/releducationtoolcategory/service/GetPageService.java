package com.example.managementsystem.endpoints.releducationtoolcategory.service;

import com.example.managementsystem.entities.RelEducationToolCategory;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericPageRequest;
import com.example.managementsystem.request.GetAllPageableRequest;
import com.example.managementsystem.response.GenericPageResponse;
import com.example.managementsystem.search.ProPageable;
import com.example.managementsystem.services.RelEducationToolCategoryService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("getPageRelEducationToolCategoryService")
public class GetPageService {

    private final RelEducationToolCategoryService relEducationToolCategoryService;

    public GetPageService(RelEducationToolCategoryService relEducationToolCategoryService) {
        this.relEducationToolCategoryService = relEducationToolCategoryService;
    }

    public GenericPageResponse<RelEducationToolCategory> service(GenericPageRequest<String> request, GenericPageResponse<RelEducationToolCategory> response) {
        try {
            String predicate = request.getPredicate();
            GetAllPageableRequest getAllPageableRequest = new GetAllPageableRequest(new ProPageable(request.getPageable()), predicate);
            Page<RelEducationToolCategory> page = relEducationToolCategoryService.getAllPageable(getAllPageableRequest);

            response.setStatus(CommonStatus.OK.toString());
            response.setPage(page);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
