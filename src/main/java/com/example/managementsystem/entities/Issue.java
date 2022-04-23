package com.example.managementsystem.entities;

import com.example.managementsystem.enumeration.IssueStatus;
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

    @Column(name = "severity")
    private String severity;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "issueId")
    List<IssueLog> issueLogs;
}
