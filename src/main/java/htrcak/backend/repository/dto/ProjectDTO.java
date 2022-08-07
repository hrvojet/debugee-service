package htrcak.backend.repository.dto;

import htrcak.backend.repository.models.Issue;

import java.util.Set;

public class ProjectDTO {

    private long id;

    private String title;

    private String description;

    private Set<IssueDTO> issues;

    public ProjectDTO(long id, String title, String description, Set<IssueDTO> issues) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.issues = issues;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Set<IssueDTO> getIssues() {
        return issues;
    }
}
