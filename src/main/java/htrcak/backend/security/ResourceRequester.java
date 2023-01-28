package htrcak.backend.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class ResourceRequester implements Serializable {

    @Serial
    private static final long serialVersionUID = -437194705031980405L;

    private String id;

    private String username;

    private String email;

    private List<SimpleGrantedAuthority> authorities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
