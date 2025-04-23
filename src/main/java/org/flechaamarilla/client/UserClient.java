package org.flechaamarilla.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.flechaamarilla.dto.UserDTO;

@RegisterRestClient
public interface UserClient {

    @GET
    @Path("/api/user/byEmail/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    UserDTO getUserByEmail(@PathParam("email") String email);
}