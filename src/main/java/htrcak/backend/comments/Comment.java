package htrcak.backend.comments;

import htrcak.backend.issues.Issue;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    private long id;

    @Column
    private String author;

    @Column
    private String text;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime edited;

    @ManyToOne
    @JoinColumn(name = "issue_id", referencedColumnName = "id")
    private Issue issue;

    public Comment() {
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
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
}
