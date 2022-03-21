package com.example.managementsystem.endpoints.tool.service;

import com.example.managementsystem.entities.Tool;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.ToolService;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Service;

@Service("toolSaveService")
public class SaveService {
    private final ToolService toolService;

    public SaveService(ToolService toolService) {
        this.toolService = toolService;
    }

    public GenericSingleResponse<Tool> service(GenericSingleRequest<Tool> request, GenericSingleResponse<Tool> response) {
        try {
            Tool Tool = toolService.save(request.getParam());
            response.setItem(Tool);
            response.setStatus(CommonStatus.OK.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
