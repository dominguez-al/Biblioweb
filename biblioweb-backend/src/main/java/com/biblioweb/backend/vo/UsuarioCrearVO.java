package com.biblioweb.backend.vo;

/**
 * VO (Value Object) especializado para la creación de usuarios.
 * Este objeto encapsula únicamente los datos necesarios para registrar
 * un nuevo usuario, incluyendo campos sensibles como la contraseña.
 *
 * Se usa típicamente en el endpoint POST /usuarios/crear o /auth/registro.
 */
public class UsuarioCrearVO {

    // Nombre del usuario
    private String nombre;

    // Correo electrónico del usuario (sirve como identificador de login)
    private String email;

    // Contraseña en texto plano (será encriptada antes de guardarse)
    private String password;

    // Constructor vacío obligatorio para frameworks como Spring y Jackson
    public UsuarioCrearVO() {}
    
    // Rol del usuario
    private String rol;
    
    

    /**
     * Constructor completo, útil para inicializaciones manuales o pruebas.
     */
    public UsuarioCrearVO(String nombre, String email, String password, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    // --- Getters y Setters ---

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}

