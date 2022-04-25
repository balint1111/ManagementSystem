package com.example.managementsystem.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "issue_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "issue_id", nullable = false, updatable = false)
    private Long issueId;

    @Column(name = "description", updatable = false)
    private String description;

    @Column(name = "justification", updatable = false)
    private String justification;

    @Column(name = "created_on", nullable = false, updatable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Instant createdOn = Instant.now();

    public IssueLog(Long id, Long issueId, String description, String justification) {
        this.id = id;
        this.issueId = issueId;
        this.description = description;
        this.justification = justification;
    }
}
