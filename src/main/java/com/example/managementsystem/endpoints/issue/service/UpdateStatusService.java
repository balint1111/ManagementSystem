package com.example.managementsystem.endpoints.issue.service;

import com.example.managementsystem.entities.Issue;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.request.issue.IssueUpdateStatusRequest;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.services.IssueService;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Service;

@Service("issueUpdateStatusService")
public class UpdateStatusService {
    private final IssueService issueService;

    public UpdateStatusService(IssueService IssueService) {
        this.issueService = IssueService;
    }

    public GenericSingleResponse<Issue> service(IssueUpdateStatusRequest request, GenericSingleResponse<Issue> response) {
        try {
            Issue saved = issueService.updateStatus(request.getIssueId(), request.getNewStatus(), request.getJustification());
            response.setItem(saved);
            response.setStatus(CommonStatus.OK.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(CommonStatus.ERROR.toString());
            response.setCause(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
