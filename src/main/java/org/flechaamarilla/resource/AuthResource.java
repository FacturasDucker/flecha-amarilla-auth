package org.flechaamarilla.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.flechaamarilla.dto.LoginRequestDTO;
import org.flechaamarilla.dto.LoginResponseDTO;
import org.flechaamarilla.service.AuthService;

import java.util.Map;

/**
 * Resource exposing authentication endpoints
 */
@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "Authentication operations")
public class AuthResource {

    @Inject
    AuthService authService;

    @Inject
    JsonWebToken jwt;


    /**
     * Authenticate a user and get JWT token
     *
     * @param request login credentials
     * @return response with token and user details
     */
    @POST
    @Path("/login")
    @PermitAll
    @Operation(summary = "Login", description = "Authenticates a user and returns a JWT token")
    public Response login(@Valid LoginRequestDTO request) {



        LoginResponseDTO response = authService.login(request);
        return Response.ok(response).build();
    }

    /**
     * Protected endpoint example - only for authenticated users
     * Returns information about the currently authenticated user
     */
    @GET
    @Path("/me")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Get current user", description = "Returns information about the authenticated user")
    public Response getUserInfo(@Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();

        //The authentication service is responsible for finding the user and handling exceptions
        return Response.ok(Map.of(
                "email", email,
                "auth", Map.of(
                        "isAuthenticated", true,
                        "roles", jwt.getGroups()
                )
        )).build();
    }
}
