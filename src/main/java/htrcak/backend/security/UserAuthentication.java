package htrcak.backend.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;
import java.util.Objects;

public class UserAuthentication extends AbstractAuthenticationToken {


    @Serial
    private static final long serialVersionUID = 8339339545314305767L;

    private final ApplicationUser applicationUser;

    public UserAuthentication(ApplicationUser applicationUser) {
        super(applicationUser.getAuthorities());
        this.applicationUser = applicationUser;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "NO";
    }

    @Override
    public Object getPrincipal() {
        return applicationUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), applicationUser);
    }
}
