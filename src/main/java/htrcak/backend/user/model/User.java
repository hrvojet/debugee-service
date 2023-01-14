package htrcak.backend.user.model;

import htrcak.backend.comments.Comment;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "gitlab_user")
public class User {

    @Id
    private long id;

    @Column
    private String username;

    @Column
    private String email;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
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
}
