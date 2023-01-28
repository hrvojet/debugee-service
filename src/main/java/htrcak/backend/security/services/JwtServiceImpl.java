package htrcak.backend.security.services;

import htrcak.backend.security.ResourceRequester;
import htrcak.backend.security.UserAuthentication;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class JwtServiceImpl implements JwtService{

    private static final Logger log = LoggerFactory.getLogger(JwtServiceImpl.class);
    private static final String AUTHORITIES_KEY = "authorities";

    private final String signingKey = "signingKeyForSigningJWTtokens";
    private final RestTemplate restTemplate;

    private String gitLabUri;

    public JwtServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public boolean authenticate(String token) {
        if (isJwtInvalid(token)) {
            return false;
        }
        ResourceRequester resourceRequester = getUserDataFromJwt(token);
        saveAuthentication(resourceRequester);
        return true;
    }

    @Override
    public String generateJWT(String accessTokenGitLab) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessTokenGitLab);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // TODO add to properties GitLab instance uri and append
        ResponseEntity<Map> response = restTemplate.exchange("http://192.168.99.101/api/v4/user", HttpMethod.GET, request, Map.class);

        String isAdmin = (response.getBody() != null && response.getBody().get("is_admin") != null && (boolean)response.getBody().get("is_admin")) ? "ADMIN" : "";
        //Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setSubject(response.getBody().get("id").toString())
                .claim("username", response.getBody().get("username").toString())
                .claim("email", response.getBody().get("email"))
                .claim("authorities", isAdmin.compareTo("") == 0 ? Collections.emptyList() : isAdmin)
                .setExpiration(new Date(System.currentTimeMillis() + 7000000))
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }

    private boolean isJwtInvalid(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwtToken);
            return false;
        } catch (SignatureException e) {
            log.debug("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.debug("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.debug("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.debug("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.debug("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e.getMessage());
        }
        return true;
    }

    private ResourceRequester getUserDataFromJwt(String jwtToken) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(jwtToken)
                .getBody();

        List<SimpleGrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        ResourceRequester resourceRequester = new ResourceRequester();
        resourceRequester.setUsername(claims.get("username").toString());
        resourceRequester.setEmail(claims.get("email").toString());
        resourceRequester.setId(claims.getSubject());
        resourceRequester.setAuthorities(authorities);

        return resourceRequester;
    }

    private void saveAuthentication(ResourceRequester resourceRequester) {
        Authentication authentication = new UserAuthentication(resourceRequester);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
