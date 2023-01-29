package htrcak.backend.core.projects.data;

import htrcak.backend.core.user.model.User;
import htrcak.backend.core.user.model.UserDTO;

public class ProjectDTO {

    private long id;

    private String title;

    private String description;

    private int openedIssues;

    private int closedIssues;

    private UserDTO owner;

    public ProjectDTO(long id, String title, String description, int openedIssues, int closedIssues, User owner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.closedIssues = closedIssues;
        this.openedIssues = openedIssues;
        this.owner = new UserDTO(owner.getId(), owner.getName(), owner.getEmail()); // todo -> maybe not mix entities? pass dto, handle it out of the constructor
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
}
