package com.biblioweb.backend.dto;

/**
 * DTO para capturar los datos de inicio de sesión enviados por el cliente.
 * Este objeto se usa únicamente para recibir el email y la contraseña del usuario al hacer login.
 */
public class LoginRequest {

    // Campo para el email del usuario
    private String email;

    // Campo para la contraseña en texto plano (será comparada con la encriptada)
    private String password;

    // Getter y Setter para email
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Getter y Setter para password
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
