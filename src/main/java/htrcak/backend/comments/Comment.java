package htrcak.backend.comments;

import htrcak.backend.issues.Issue;
import htrcak.backend.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    private long id;

    @Column
    private String text;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime edited;

    @ManyToOne
    @JoinColumn(name = "issue_id", referencedColumnName = "id")
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    public Comment() {
    }

    public long getId() {
        return id;
    }


    public String getText() {
        return text;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getEdited() {
        return edited;
    }

    public Issue getIssue() {
        return issue;
    }

    public User getUser() {
        return author;
    }
}
