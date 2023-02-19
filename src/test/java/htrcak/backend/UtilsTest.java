package htrcak.backend;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class UtilsTest {

    public static String generateAdminAccessToken() {
        return Jwts.builder()
                .setSubject(String.valueOf(7))
                .claim("username", "debugee")
                .claim("email", "de@bug.e")
                .claim("authorities", "ADMIN")
                .setExpiration(new Date(System.currentTimeMillis() + 70000000))
                .signWith(SignatureAlgorithm.HS512, "signingKeyForSigningJWTtokens")
                .compact();
    }

    public static String generateRegularAccessToken() {
        return Jwts.builder()
                .setSubject(String.valueOf(7))
                .claim("username", "developer")
                .claim("email", "developer@example.com")
                .claim("authorities", "")
                .setExpiration(new Date(System.currentTimeMillis() + 70000000))
                .signWith(SignatureAlgorithm.HS512, "signingKeyForSigningJWTtokens")
                .compact();
    }
}
