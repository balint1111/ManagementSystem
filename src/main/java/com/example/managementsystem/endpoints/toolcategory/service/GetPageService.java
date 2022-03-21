package com.example.managementsystem.endpoints.toolcategory.service;

import com.example.managementsystem.entities.RelEducationUser;
import com.example.managementsystem.entities.ToolCategory;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericPageRequest;
import com.example.managementsystem.request.GetAllPageableRequest;
import com.example.managementsystem.response.GenericPageResponse;
import com.example.managementsystem.search.ProPageable;
import com.example.managementsystem.services.RelEducationUserService;
import com.example.managementsystem.services.ToolCategoryService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("getPageToolCategoryService")
public class GetPageService {

    private final ToolCategoryService toolCategoryService;

    public GetPageService(ToolCategoryService toolCategoryService) {
        this.toolCategoryService = toolCategoryService;
    }

    public GenericPageResponse<ToolCategory> service(GenericPageRequest<String> request, GenericPageResponse<ToolCategory> response) {
        try {
            String predicate = request.getPredicate();
            GetAllPageableRequest getAllPageableRequest = new GetAllPageableRequest(new ProPageable(request.getPageable()), predicate);
            Page<ToolCategory> page = toolCategoryService.getAllPageable(getAllPageableRequest);

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
