package htrcak.backend.comments;

import htrcak.backend.issues.Issue;
import htrcak.backend.user.model.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String text;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    @UpdateTimestamp
    private LocalDateTime edited;

    @ManyToOne
    @JoinColumn(name = "issue_id", referencedColumnName = "id")
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    public Comment() {
    }

    public Comment(User author, String text, Issue issue) {
        this.author = author;
        this.text = text;
        this.issue = issue;
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

    public void setText(String text) {
        this.text = text;
    }
}
