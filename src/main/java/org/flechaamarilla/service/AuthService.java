package org.flechaamarilla.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.flechaamarilla.client.UserClient;
import org.flechaamarilla.dto.LoginRequestDTO;
import org.flechaamarilla.dto.LoginResponseDTO;
import org.flechaamarilla.dto.UserDTO;
import org.flechaamarilla.exception.AuthenticationException;
import org.flechaamarilla.security.PasswordService;
import org.flechaamarilla.security.TokenService;

/**
 * Service for handling user authentication and registration
 */
@ApplicationScoped
public class AuthService {

    
    @Inject
    PasswordService passwordService;
    
    @Inject
    TokenService tokenService;

    @Inject
    @RestClient
    UserClient userClient;



    /**
     * Authenticate a user and generate JWT token
     * @param loginRequest DTO containing login credentials
     * @return response with authentication token and user details
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {

        UserDTO user;

        try {
            // Attempt to get user from the service
            user = userClient.getUserByEmail(loginRequest.getEmail());

            // Check if returned user is null (some implementations might return null instead of throwing)
            if (user == null) {
                throw new AuthenticationException("Usuario no encontrado");
            }

        } catch (WebApplicationException e) {
            // REST client converts HTTP errors (like 404) to WebApplicationException
            if (e.getResponse().getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                throw new AuthenticationException("Usuario no encontrado con el email: " + loginRequest.getEmail());
            }
            throw new AuthenticationException("Error al verificar usuario: " + e.getMessage());
        }

        // Verify password
        if (!passwordService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Email o contraseña inválidos");
        }
        
        // Generate JWT token
        String token = tokenService.generateToken(user);
        
        // Return response with token and user details

        return new LoginResponseDTO(
            token,
            user.getEmail(),
            user.getName(),
            "user" // Rol por defecto
        );
    }
}