package com.biblioweb.backend.entity;

import jakarta.persistence.*; // Anotaciones de JPA
import java.time.LocalDate;
import java.util.List;

@Entity // Marca esta clase como una entidad de JPA (es decir, una tabla en la BD)
@Table(name = "usuario") // Asocia esta clase con la tabla 'usuario' en la base de datos
public class Usuario {

    @Id // Define el campo que actúa como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor del ID se genera automáticamente (auto-incremento en MySQL)
    private Long idUsuario;

    // Campos simples que se almacenan como columnas en la tabla
    private String nombre;
    @Column(unique = true) // No se permiten duplicados en el campo email
    private String email;
    private String password;
    private LocalDate fAltaUsuario; // Fecha en la que se dio de alta el usuario
    @Column(nullable = false)
    private String rol; // puede ser "ADMIN" o "USER
    @Column(name = "reset_token")
    private String resetToken;// Recuperar contraseña


    @OneToMany(mappedBy = "usuario") // Relación uno-a-muchos: un usuario puede tener muchas reservas de libros
    private List<UsuarioLibro> reservasLibro;

    @OneToMany(mappedBy = "usuario") // Relación uno-a-muchos: un usuario puede tener muchas reservas de aulas
    private List<UsuarioAula> reservasAula;

    @PrePersist
    protected void onCreate() {
        // Este método se ejecuta automáticamente antes de insertar un nuevo registro
        // Asigna la fecha actual a fAltaUsuario si no se ha especificado
        this.fAltaUsuario = LocalDate.now();
    }

    // Constructor vacío requerido por JPA
    public Usuario() {}

    // Constructor personalizado para crear usuarios con datos básicos
    public Usuario(Long idUsuario, String nombre, String email, String password) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters para todos los campos. Necesarios para que JPA acceda a ellos.
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getFAltaUsuario() {
        return fAltaUsuario;
    }

    public void setFAltaUsuario(LocalDate fAltaUsuario) {
        this.fAltaUsuario = fAltaUsuario;
    }

    public List<UsuarioLibro> getReservasLibro() {
        return reservasLibro;
    }

    public void setReservasLibro(List<UsuarioLibro> reservasLibro) {
        this.reservasLibro = reservasLibro;
    }

    public List<UsuarioAula> getReservasAula() {
        return reservasAula;
    }

    public void setReservasAula(List<UsuarioAula> reservasAula) {
        this.reservasAula = reservasAula;
    }

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
    
	public String getResetToken() {
	    return resetToken;
	}

	public void setResetToken(String resetToken) {
	    this.resetToken = resetToken;
	}
    
}

