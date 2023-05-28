package htrcak.backend.core.user.model;

public class UserDTO {

    // todo user controller (latest messages, inbox, replys etc.)

    private long id;

    private String username;

    private String email;

    private String avatar_url;

    private String web_url;

    public UserDTO(long id, String username, String email, String avatarUrl, String webUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatar_url = avatarUrl;
        this.web_url = webUrl;
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

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getWeb_url() {
        return web_url;
    }
}
