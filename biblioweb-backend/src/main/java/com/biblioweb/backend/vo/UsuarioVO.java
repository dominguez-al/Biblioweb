package com.biblioweb.backend.vo;

import java.time.LocalDate;

/**
 * Value Object (VO) que representa los datos visibles de un Usuario
 * Este VO es usado para enviar o recibir información a través de la API
 * No incluye la contraseña por motivos de seguridad
 */
public class UsuarioVO {

    private Long id;
    private String nombre;
    private String email;
    private LocalDate fAltaUsuario;

    public UsuarioVO() {}

    public UsuarioVO(Long id, String nombre, String email, LocalDate fAltaUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.fAltaUsuario = fAltaUsuario;
    }

    // Getters y setters estándar
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFAltaUsuario() {
        return fAltaUsuario;
    }

    public void setFAltaUsuario(LocalDate fAltaUsuario) {
        this.fAltaUsuario = fAltaUsuario;
    }
}
