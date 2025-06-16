package com.example.auth.util;

import com.example.auth.model.User; // Assuming User model is needed for claims
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${app.jwtSecret}") // You'll need to define this in application.properties
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}") // And this one too
    private int jwtExpirationMs;

    public String generateJwtToken(User userPrincipal) { // Or based on UserDetails if using Spring Security's User
        Map<String, Object> claims = new HashMap<>();
        // Add claims like username, roles etc.
        // For now, let's keep it simple with email as subject
        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            // Log exception (SignatureException, MalformedJwtException, ExpiredJwtException, etc.)
        }
        return false;
    }
}
