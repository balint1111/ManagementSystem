package com.example.managementsystem.endpoints.tool.service;

import com.example.managementsystem.entities.Tool;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.services.ToolService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("listToolService")
public class ListService {

    private final ToolService toolService;

    public ListService(ToolService toolService) {
        this.toolService = toolService;
    }

    public GenericListResponse<Tool> service(GenericSingleRequest<String> request, GenericListResponse<Tool> response) {
        try {
            String predicate = request.getParam();
            List<Tool> list = toolService.getAll(predicate);

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
