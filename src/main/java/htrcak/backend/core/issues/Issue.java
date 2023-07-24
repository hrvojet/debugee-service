package htrcak.backend.core.issues;

import htrcak.backend.core.comments.Comment;
import htrcak.backend.core.label.Label;
import htrcak.backend.core.projects.Project;
import htrcak.backend.core.user.model.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Issue {
    // TODO add timestamps (možda za create, samo uzmeš info iz prvog komentara?)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    @UpdateTimestamp
    private LocalDateTime edited;

    @Column
    private int commentNumber;

    @Column
    private String issueType; //TODO labels

    @ManyToOne
    @JoinColumn(name="project_id", referencedColumnName = "id")
    private Project project;

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Comment> comments;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "originalPoster")
    @NotNull
    private User originalPoster;

    @Column
    private boolean isOpened;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "issue_label",
            joinColumns = { @JoinColumn(name = "issue_id") },
            inverseJoinColumns = { @JoinColumn(name = "label_id") }
    )
    private Set<Label> labelsSet = new HashSet<>();

    public Issue() {
    }

    public Issue(Project project, String title, String issueType, User op) {
        this.project = project;
        this.title = title;
        this.issueType = Objects.requireNonNullElse(issueType, "");
        this.originalPoster = op;
        this.isOpened = true;
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

    public User getOriginalPoster() {
        return originalPoster;
    }

    public void setOriginalPoster(User originalPoster) {
        this.originalPoster = originalPoster;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getEdited() {
        return edited;
    }

    public void setEdited(LocalDateTime edited) {
        this.edited = edited;
    }

    public Set<Label> getLabelsSet() {
        return labelsSet;
    }

    public void setLabelsSet(Set<Label> labelsSet) {
        this.labelsSet = labelsSet;
    }

    public void addLabel(Label label) {
        labelsSet.add(label);
        label.getUsedIssues().add(this);
    }

    public void removeLabel(Label label) {
        labelsSet.remove(label);
        label.getUsedIssues().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if(!(o instanceof Issue))
            return false;

        return id != null && id.equals(((Issue) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
