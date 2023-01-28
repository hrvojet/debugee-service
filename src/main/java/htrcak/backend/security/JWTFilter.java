package htrcak.backend.security;

import htrcak.backend.security.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";

    private static final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    private final JwtService jwtService;

    public JWTFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());

        if (!allowUnauthorised(req)) {
            String jwtToken = extractJwtToken(req);

            log.trace("doFilter for endpoint: {} resolved jwt: {}", req.getRequestURI(), jwtToken);

            if (jwtToken != null && !jwtToken.isEmpty()) {
                boolean authenticate = jwtService.authenticate(jwtToken);

                if (!authenticate) {
                    unauthorized(res);
                }
            } else {
                unauthorized(res);
            }
        }

        fc.doFilter(req, res);

    }

    private void unauthorized(HttpServletResponse res) {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private String extractJwtToken(HttpServletRequest request) {
        String accessToken = request.getHeader(AUTHORIZATION_HEADER);
        if (accessToken != null && accessToken.startsWith(AUTHORIZATION_TOKEN_PREFIX)) {
            return accessToken.substring(AUTHORIZATION_TOKEN_PREFIX.length());
        }
        return null;
    }

    private boolean allowUnauthorised(HttpServletRequest req) {
        String uri = req.getRequestURI();
        return Arrays.stream(SecurityConfig.UNAUTHENTICATED_ENDPOINTS)
                .toList()
                .stream()
                .anyMatch(endpoint -> uri.contains(endpoint.replace("**", "")));
    }
}
