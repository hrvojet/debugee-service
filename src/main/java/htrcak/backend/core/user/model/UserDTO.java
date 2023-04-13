package htrcak.backend.core.user.model;

public class UserDTO {

    // todo user controller (latest messages, inbox, replys etc.)

    private long id;

    private String username;

    private String email;

    public UserDTO(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
