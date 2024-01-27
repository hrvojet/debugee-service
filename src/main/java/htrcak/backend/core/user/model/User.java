package htrcak.backend.core.user.model;

import htrcak.backend.core.comments.Comment;
import htrcak.backend.core.issues.Issue;
import htrcak.backend.core.projects.Project;
import io.micrometer.core.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gitlab_user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -7466453060188825579L;

    @Id
    @NotNull
    private long id;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private boolean isAdmin;

    @Column
    private String avatarUrl;

    @Column
    private String webUrl;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private Set<Project> project = new HashSet<>();

    @OneToMany(mappedBy = "originalPoster", fetch = FetchType.LAZY)
    private Set<Issue> issues = new HashSet<>();


    @ManyToMany(mappedBy = "userFavourite")
    private Set<Project> favouriteProjects = new HashSet<>();

    public User(String username, String email, long id, boolean isAdmin, String webUrl, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isAdmin = isAdmin;
        this.webUrl = webUrl;
        this.avatarUrl = avatarUrl;
    }

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Project> getProjectOwner() {
        return project;
    }

    public void setProjectOwner(Set<Project> projectOwner) {
        this.project = projectOwner;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Set<Project> getFavouriteProjects() {
        return favouriteProjects;
    }

    public void setFavouriteProjects(Set<Project> favouriteProjects) {
        this.favouriteProjects = favouriteProjects;
    }

    public void addProjectToFavourites(Project project) {
        favouriteProjects.add(project);
        project.getUserFavourite().add(this);
    }

    public void removeProjectFromFavourites(Project project) {
        favouriteProjects.remove(project);
        project.getUserFavourite().remove(this);
    }
}
