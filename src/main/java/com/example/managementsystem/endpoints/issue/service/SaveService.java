package com.example.managementsystem.endpoints.issue.service;

import com.example.managementsystem.entities.Issue;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.IssueService;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Service;

@Service("issueSaveService")
public class SaveService {
    private final IssueService issueService;

    public SaveService(IssueService IssueService) {
        this.issueService = IssueService;
    }

    public GenericSingleResponse<Issue> service(GenericSingleRequest<Issue> request, GenericSingleResponse<Issue> response) {
        try {
            Issue Issue = issueService.save(request.getParam());
            response.setItem(Issue);
            response.setStatus(CommonStatus.OK.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
