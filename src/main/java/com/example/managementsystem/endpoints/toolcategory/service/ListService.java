package com.example.managementsystem.endpoints.toolcategory.service;

import com.example.managementsystem.entities.ToolCategory;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.services.ToolCategoryService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("listToolCategoryService")
public class ListService {

    private final ToolCategoryService toolCategoryService;

    public ListService(ToolCategoryService toolCategoryService) {
        this.toolCategoryService = toolCategoryService;
    }

    public GenericListResponse<ToolCategory> service(GenericSingleRequest<String> request, GenericListResponse<ToolCategory> response) {
        try {
            String predicate = request.getParam();
            List<ToolCategory> list = toolCategoryService.getAll(predicate);

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
