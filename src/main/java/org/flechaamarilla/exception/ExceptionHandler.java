package org.flechaamarilla.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Global exception handler for mapping exceptions to appropriate HTTP responses
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        // Handle different types of exceptions with appropriate HTTP status codes
        if (exception instanceof AuthenticationException) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse(exception.getMessage(), 401))
                    .build();
        } else if (exception instanceof UserNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(exception.getMessage(), 404))
                    .build();
        } else if (exception instanceof UserAlreadyExistsException) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse(exception.getMessage(), 409))
                    .build();
        } else if (exception instanceof jakarta.validation.ConstraintViolationException) {
            // Handle validation errors
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Validation error: " + exception.getMessage(), 400))
                    .build();
        }
        
        // Default to internal server error for unhandled exceptions
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Internal server error", 500))
                .build();
    }
    
    /**
     * DTO for standardized error responses
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorResponse {
        private String message;
        private int status;
    }
}