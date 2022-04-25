package com.example.managementsystem.request.issue;

import com.example.managementsystem.enumeration.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueUpdateStatusRequest {
    private Long issueId;
    private IssueStatus newStatus;
    private String justification;
}
