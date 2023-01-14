package htrcak.backend.comments.data;

import htrcak.backend.user.model.User;
import htrcak.backend.user.model.UserDTO;

import java.time.LocalDateTime;

public class CommentDTO {


    private String text;

    private LocalDateTime created;

    private LocalDateTime edited;

    private UserDTO author;

    public CommentDTO(UserDTO author, String text, LocalDateTime created, LocalDateTime edited) {
        this.author = author;
        this.text = text;
        this.created = created;
        this.edited = edited;
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

    public UserDTO getAuthor() {
        return author;
    }
}
