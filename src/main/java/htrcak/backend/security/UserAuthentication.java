package htrcak.backend.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.io.Serial;
import java.util.Objects;

public class UserAuthentication extends AbstractAuthenticationToken {


    @Serial
    private static final long serialVersionUID = 8339339545314305767L;

    private final ResourceRequester resourceRequester;

    public UserAuthentication(ResourceRequester resourceRequester) {
        super(resourceRequester.getAuthorities());
        this.resourceRequester = resourceRequester;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "NO";
    }

    @Override
    public Object getPrincipal() {
        return resourceRequester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), resourceRequester);
    }
}
