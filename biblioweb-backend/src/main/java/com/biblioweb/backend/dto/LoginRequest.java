package com.biblioweb.backend.dto;

/**
 * DTO (Data Transfer Object) para capturar los datos de inicio de sesión
 * enviados por el cliente al backend. Se utiliza en el endpoint de login.
 */
public class LoginRequest {

    // Campo para el email del usuario que intenta iniciar sesión
    private String email;

    // Campo para la contraseña en texto plano (luego será comparada con la versión encriptada en la BD)
    private String password;

    // Getter para el campo email
    public String getEmail() {
        return email;
    }

    // Setter para el campo email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter para el campo password
    public String getPassword() {
        return password;
    }

    // Setter para el campo password
    public void setPassword(String password) {
        this.password = password;
    }
}
