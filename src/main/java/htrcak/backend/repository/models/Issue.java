package htrcak.backend.repository.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Issue {

    @Id
    private long id;

    @Column
    private String title;

    @Column
    private int commentNumber;

    @Column
    private String issueType; //TODO enum

    @ManyToOne
    @JoinColumn(name="project_id", referencedColumnName = "id")
    private Project project;

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Comment> comments;

    public Issue() {
    }

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

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public String getType() {
        return issueType;
    }

    public void setType(String type) {
        this.issueType = type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
