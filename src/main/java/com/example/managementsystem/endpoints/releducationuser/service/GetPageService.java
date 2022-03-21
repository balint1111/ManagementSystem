package com.example.managementsystem.endpoints.releducationuser.service;

import com.example.managementsystem.entities.RelEducationUser;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericPageRequest;
import com.example.managementsystem.request.GetAllPageableRequest;
import com.example.managementsystem.response.GenericPageResponse;
import com.example.managementsystem.search.ProPageable;
import com.example.managementsystem.services.RelEducationUserService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("getPageRelEducationUserService")
public class GetPageService {

    private final RelEducationUserService relEducationUserService;

    public GetPageService(RelEducationUserService relEducationUserService) {
        this.relEducationUserService = relEducationUserService;
    }

    public GenericPageResponse<RelEducationUser> service(GenericPageRequest<String> request, GenericPageResponse<RelEducationUser> response) {
        try {
            String predicate = request.getPredicate();
            GetAllPageableRequest getAllPageableRequest = new GetAllPageableRequest(new ProPageable(request.getPageable()), predicate);
            Page<RelEducationUser> page = relEducationUserService.getAllPageable(getAllPageableRequest);

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
