package com.example.managementsystem.endpoints.tool.service;

import com.example.managementsystem.entities.Tool;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericPageRequest;
import com.example.managementsystem.request.GetAllPageableRequest;
import com.example.managementsystem.response.GenericPageResponse;
import com.example.managementsystem.search.ProPageable;
import com.example.managementsystem.services.ToolService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("getPageToolService")
public class GetPageService {

    private final ToolService toolService;

    public GetPageService(ToolService toolService) {
        this.toolService = toolService;
    }

    public GenericPageResponse<Tool> service(GenericPageRequest<String> request, GenericPageResponse<Tool> response) {
        try {
            String predicate = request.getPredicate();
            GetAllPageableRequest getAllPageableRequest = new GetAllPageableRequest(new ProPageable(request.getPageable()), predicate);
            Page<Tool> page = toolService.getAllPageable(getAllPageableRequest);

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
