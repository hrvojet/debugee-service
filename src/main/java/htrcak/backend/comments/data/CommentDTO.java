package htrcak.backend.comments.data;

import java.time.LocalDateTime;

public class CommentDTO {

    private String author;

    private String text;

    private LocalDateTime created;

    private LocalDateTime edited;

    public CommentDTO(String author, String text, LocalDateTime created, LocalDateTime edited) {
        this.author = author;
        this.text = text;
        this.created = created;
        this.edited = edited;
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
}
