package org.flechaamarilla.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.flechaamarilla.dto.UserDTO;

@Path("/api/clients")
@RegisterRestClient
public interface ClienteClient {
    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    UserDTO getByEmail(@PathParam("email") String email);
}