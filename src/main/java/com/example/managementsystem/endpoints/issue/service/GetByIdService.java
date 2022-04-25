package com.example.managementsystem.endpoints.issue.service;

import com.example.managementsystem.entities.Issue;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.IssueService;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Service;

@Service("issueGetByIdService")
public class GetByIdService {
    private final IssueService issueService;

    public GetByIdService(IssueService issueService) {
        this.issueService = issueService;
    }

    public GenericSingleResponse<Issue> service(GenericSingleRequest<Long> request, GenericSingleResponse<Issue> response) {
        try {
            Issue issue = issueService.getById(request.getParam());
            response.setItem(issue);
            response.setStatus(CommonStatus.OK.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
