package com.example.luservice.security;

import com.example.luservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Component
public class TokenUtils {

    @Value("Literary Association")
    private String APP_NAME;

    @Value("somesecret")
    public String SECRET;

    @Value("10000")
    private int EXPIRES_IN;

    @Value("Authorization")
    private String AUTH_HEADER;

    static final String AUDIENCE_WEB = "web";

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    // Functions for generating new JWT token

    public String generateToken(User user) {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(user.getUsername())
                .setAudience(AUDIENCE_WEB)
                .setIssuedAt(now())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, SECRET)
                .claim("roles", Arrays.asList(user.getRole().getAuthority()))
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(now().getTime() + EXPIRES_IN * 1000);
    }

    // Functions for validating JWT token data

    public boolean validateToken(String token, UserDetails userDetails) {
        final Claims claims = this.getClaims(token);
        final String username = claims.getSubject();
        return (username != null && username.equals(userDetails.getUsername())  && !isTokenExpired(token));
    }


    private boolean isTokenExpired(String token) {
        final Date expiration =  this.getClaims(token).getExpiration();
        return  expiration.before(now());
    }

    // Functions for getting data from token

    public Claims getClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid access token.");
        }
        return claims;
    }

    public int getExpiredIn() {
        return EXPIRES_IN;
    }

    // Functions for getting JWT token out of HTTP request

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

    private Date now() {
        return new Date();
    }

}