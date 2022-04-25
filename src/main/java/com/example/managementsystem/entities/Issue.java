package com.example.managementsystem.entities;

import com.example.managementsystem.enumeration.IssueSeverity;
import com.example.managementsystem.enumeration.IssueStatus;
import com.example.managementsystem.enumeration.IssueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity(name = "issue")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tool_id", nullable = false)
    private Tool tool;

    @ManyToOne
    @JoinColumn(name = "responsible_user")
    private User responsibleUser;

    @Column(name = "date_time")
    private Instant dateTime;

    @Column(name = "estimated_time")
    private Integer estimatedTime;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private IssueSeverity severity;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private IssueType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "issueId")
    List<IssueLog> issueLogs;

    @Column(name = "created_on", nullable = false, updatable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Instant createdOn = Instant.now();

    public Issue(Long id, Tool tool, User responsibleUser, Instant dateTime, Integer estimatedTime, String title, IssueSeverity severity, IssueType type, IssueStatus status, String description, List<IssueLog> issueLogs) {
        this.id = id;
        this.tool = tool;
        this.responsibleUser = responsibleUser;
        this.dateTime = dateTime;
        this.estimatedTime = estimatedTime;
        this.title = title;
        this.severity = severity;
        this.type = type;
        this.status = status;
        this.description = description;
        this.issueLogs = issueLogs;
    }
}
