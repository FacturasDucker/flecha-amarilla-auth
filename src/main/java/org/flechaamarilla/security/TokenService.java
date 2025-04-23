package org.flechaamarilla.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flechaamarilla.dto.UserDTO;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Service for JWT token generation and validation
 */
@ApplicationScoped
public class TokenService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @ConfigProperty(name = "quarkus.smallrye-jwt.token.lifespan")
    long tokenLifespan;

    /**
     * Generate a new JWT token for the given user
     * @param user the authenticated user
     * @return the JWT token string
     */
    public String generateToken(UserDTO user) {
        // Current timestamp for token issuing
        Instant now = Instant.now();
        
        // Token expiration time based on configuration
        Instant expiration = now.plus(Duration.ofSeconds(tokenLifespan));

        // Create and return the signed JWT token
        return Jwt.issuer(issuer) // token issuer
                 .subject(user.getEmail()) // subject is the user email
                 .upn(user.getEmail()) // principal name is the user email
                 .groups(new HashSet<>(Arrays.asList("user"))) // default role is "user"
                 .claim("email", user.getEmail()) // custom claims
                 .claim("name", user.getName())
                 .issuedAt(now) // token issued time
                 .expiresAt(expiration) // token expiration time
                 .sign(); // sign and build the token
    }
}