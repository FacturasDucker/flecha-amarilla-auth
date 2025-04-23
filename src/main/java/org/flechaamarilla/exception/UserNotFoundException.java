package org.flechaamarilla.exception;

/**
 * Exception thrown when a user is not found
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException() {
        super("Usuario no encontrado");
    }
    
    public UserNotFoundException(String message) {
        super(message);
    }
}