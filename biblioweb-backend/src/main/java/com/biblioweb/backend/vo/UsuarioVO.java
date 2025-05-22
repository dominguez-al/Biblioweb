package com.biblioweb.backend.vo;

import java.time.LocalDate;

/**
 * Value Object (VO) que representa los datos visibles de un Usuario.
 * 
 * Este objeto es utilizado para:
 * - Retornar información del usuario al frontend de manera segura.
 * - Evitar exponer campos sensibles como la contraseña o el rol.
 */
public class UsuarioVO {

    // ID único del usuario
    private Long id;

    // Nombre del usuario (puede ser nombre completo o solo nombre de pila)
    private String nombre;

    // Correo electrónico (sirve como identificador principal para login)
    private String email;

    // Fecha de alta o registro del usuario en el sistema
    private LocalDate fAltaUsuario;
    
    // Rol del usuario
    private String rol;


    /**
     * Constructor vacío obligatorio para que frameworks como Jackson puedan
     * deserializar objetos automáticamente.
     */
    public UsuarioVO() {}

    /**
     * Constructor completo para inicializar todos los campos manualmente o desde un mapper.
     */
    public UsuarioVO(Long id, String nombre, String email, LocalDate fAltaUsuario, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.fAltaUsuario = fAltaUsuario;
        this.rol = rol;
    }

    // --- Getters y Setters ---

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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
    
}
