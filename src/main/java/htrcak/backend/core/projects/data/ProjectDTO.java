package htrcak.backend.core.projects.data;

import htrcak.backend.core.projects.Project;
import htrcak.backend.core.user.model.User;
import htrcak.backend.core.user.model.UserDTO;

import java.time.LocalDateTime;

public class ProjectDTO {

    private long id;

    private String title;

    private String description;

    private LocalDateTime created;

    private LocalDateTime edited;

    private int openedIssues;

    private int closedIssues;

    private UserDTO owner;

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.closedIssues = project.getClosedIssues();
        this.openedIssues = project.getOpenedIssues();
        this.owner = new UserDTO(project.getOwner());
        this.created = project.getCreated();
        this.edited = project.getEdited();
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

    public int getOpenedIssues() { return openedIssues; }

    public int getClosedIssues() {
        return closedIssues;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getEdited() {
        return edited;
    }
}
