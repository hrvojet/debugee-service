package htrcak.backend.security.services;


public interface JwtService {

    boolean authenticate(String token);

    String generateJWT(String glAccessToken);
}
