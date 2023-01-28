package htrcak.backend.core.projects;

import htrcak.backend.core.issues.Issue;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {
    // TODO visibility, owner

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private String description;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, orphanRemoval = true)
    //@JsonIgnore
    private Set<Issue> issues;

    @Column
    private int closedIssues;

    @Column
    private int openedIssues;

    public Project(String title, String description, int closedIssues, int openedIssues) {
        this.title = title;
        this.description = description;
        this.closedIssues = closedIssues;
        this.openedIssues = openedIssues;
    }

    public Project() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    public int getClosedIssues() {
        return closedIssues;
    }

    public void setClosedIssues(int closedIssues) {
        this.closedIssues = closedIssues;
    }

    public int getOpenedIssues() {
        return openedIssues;
    }

    public void setOpenedIssues(int openedIssues) {
        this.openedIssues = openedIssues;
    }
}
