package org.flechaamarilla.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for login responses containing authentication token and user info
 * Uses Lombok annotations for automatic generation of getters, setters, and constructors
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String email;
    private String name;
    private String roles;
}