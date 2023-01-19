package htrcak.backend.core.projects.data;

public class ProjectDTO {

    private long id;

    private String title;

    private String description;

    private int openedIssues;

    private int closedIssues;

    public ProjectDTO(long id, String title, String description, int openedIssues, int closedIssues) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.closedIssues = closedIssues;
        this.openedIssues = openedIssues;
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
}
