package com.example.managementsystem.endpoints.issue.service;

import com.example.managementsystem.entities.Issue;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.services.IssueService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("listIssueService")
public class ListService {

    private final IssueService issueService;

    public ListService(IssueService IssueService) {
        this.issueService = IssueService;
    }

    public GenericListResponse<Issue> service(GenericSingleRequest<String> request, GenericListResponse<Issue> response) {
        try {
            String predicate = request.getParam();
            List<Issue> list = issueService.getAll(predicate);

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
