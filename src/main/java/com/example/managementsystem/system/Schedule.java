package com.example.managementsystem.system;

import com.example.managementsystem.entities.Issue;
import com.example.managementsystem.entities.Tool;
import com.example.managementsystem.enumeration.IssueSeverity;
import com.example.managementsystem.enumeration.IssueStatus;
import com.example.managementsystem.enumeration.IssueType;
import com.example.managementsystem.services.IssueService;
import com.example.managementsystem.services.ToolService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Component
public class Schedule {

    private final IssueService issueService;
    private final ToolService toolService;

    public Schedule(IssueService issueService, ToolService toolService) {
        this.issueService = issueService;
        this.toolService = toolService;
    }

    @SneakyThrows
    @Scheduled(cron = "*/5 * * * * *", zone = "Europe/Budapest")
    public void scheduledIssue() {
        log.info("IssueScheduled");

        List<Tool> tools = toolService.listAll();
        for (Tool tool : tools) {
            Issue lastByTool = issueService.getLastByTool(tool.getId());

            if (lastByTool == null || lastByTool.getId() == null) {
                issueService.save(new Issue(null, tool, null, ZonedDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).toInstant(),
                        tool.getToolCategory().getMaintenanceEstimatedTime(), tool.getName() + " Karbantartás", IssueSeverity.NORMAL, IssueType.MAINTENANCE, IssueStatus.NEW,
                        tool.getToolCategory().getMaintenanceDescription(), null));
                return;
            }

            ZonedDateTime lastIssueDate = lastByTool.getDateTime().atZone(ZoneId.of("Europe/Budapest"));
            ZonedDateTime now = ZonedDateTime.now();

            switch (tool.getToolCategory().getMaintenanceIntervalPro()) {
                case WEEK:
                    if (now.minusWeeks(1).plusDays(1).compareTo(lastIssueDate) > 0) {
                        Issue newIssue = getNewIssue(lastByTool);
                        newIssue.setDateTime(lastIssueDate.plusWeeks(1L).withHour(8).withMinute(0).withSecond(0).toInstant());
                        issueService.save(newIssue);
                    }
                    break;
                case MONTH:
                    if (now.minusMonths(1).plusDays(1).compareTo(lastIssueDate) > 0) {
                        Issue newIssue = getNewIssue(lastByTool);
                        newIssue.setDateTime(lastIssueDate.plusMonths(1L).withHour(8).withMinute(0).withSecond(0).toInstant());
                        issueService.save(newIssue);
                    }
                    break;
                case QUARTER:
                    if (now.minusMonths(3).plusDays(1).compareTo(lastIssueDate) > 0) {
                        Issue newIssue = getNewIssue(lastByTool);
                        newIssue.setDateTime(lastIssueDate.plusMonths(3L).withHour(8).withMinute(0).withSecond(0).toInstant());
                        issueService.save(newIssue);
                    }
                    break;
                case HALF_YEAR:
                    if (now.minusMonths(6).plusDays(1).compareTo(lastIssueDate) > 0) {
                        Issue newIssue = getNewIssue(lastByTool);
                        newIssue.setDateTime(lastIssueDate.plusMonths(6L).withHour(8).withMinute(0).withSecond(0).toInstant());
                        issueService.save(newIssue);
                    }
                    break;
                case YEAR:
                    if (now.minusYears(1).plusDays(1).compareTo(lastIssueDate) > 0) {
                        Issue newIssue = getNewIssue(lastByTool);
                        newIssue.setDateTime(lastIssueDate.plusYears(1L).withHour(8).withMinute(0).withSecond(0).toInstant());
                        issueService.save(newIssue);
                    }
                    break;
            }
        }
    }

    private Issue getNewIssue(Issue lastByTool) {
        Tool tool = lastByTool.getTool();
        return new Issue(null, tool, null, null,
                tool.getToolCategory().getMaintenanceEstimatedTime(), tool.getName() + " Karbantartás", IssueSeverity.NORMAL, IssueType.MAINTENANCE, IssueStatus.NEW,
                tool.getToolCategory().getMaintenanceDescription(), null);
    }
}
