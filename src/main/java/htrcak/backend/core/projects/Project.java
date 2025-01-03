package htrcak.backend.core.projects;

import htrcak.backend.core.issues.Issue;
import htrcak.backend.core.label.Label;
import htrcak.backend.core.user.model.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    @UpdateTimestamp
    private LocalDateTime edited;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Issue> issues;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY,  orphanRemoval = true)
    private Set<Label> labels;

    @Column
    private int closedIssues;

    @Column
    private int openedIssues;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner")
    @NotNull
    private User owner;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_favourite_project",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> userFavourite = new HashSet<>();

    public Project(String title, String description, int closedIssues, int openedIssues, User owner) {
        this.title = title;
        this.description = description;
        this.closedIssues = closedIssues;
        this.openedIssues = openedIssues;
        this.owner = owner;
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

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public Set<User> getUserFavourite() {
        return userFavourite;
    }

    public void addProjectAsFavourite(User user) {
        userFavourite.add(user);
        user.getFavouriteProjects().add(this);
    }

    public void removeProjectAsFavourite(User user) {
        userFavourite.remove(user);
        user.getFavouriteProjects().remove(this);
    }

    public void setUserFavourite(Set<User> userFavourite) {
        this.userFavourite = userFavourite;
    }
}
