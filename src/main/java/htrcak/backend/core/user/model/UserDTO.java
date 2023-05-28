package htrcak.backend.core.user.model;

public class UserDTO {

    // todo user controller (latest messages, inbox, replys etc.)

    private long id;

    private String username;

    private String email;

    private String avatarUrl;

    private String webUrl;

    public UserDTO(long id, String username, String email, String avatarUrl, String webUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.webUrl = webUrl;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
