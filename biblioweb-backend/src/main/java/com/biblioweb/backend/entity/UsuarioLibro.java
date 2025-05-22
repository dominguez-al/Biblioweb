package com.biblioweb.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity // Declara esta clase como una entidad de JPA
@Table(name = "usuario_libro") // La tabla en la base de datos asociada a esta entidad
public class UsuarioLibro {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID (auto-incremental)
    @Column(name = "id_reserva_libro")
    private Long idReservaLibro;

    // Relación muchos-a-uno: muchas reservas pueden estar asociadas a un mismo usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario") // Clave foránea a la tabla 'usuario'
    private Usuario usuario;

    // Relación muchos-a-uno: un libro puede estar reservado muchas veces
    @ManyToOne
    @JoinColumn(name = "id_libro") // Clave foránea a la tabla 'libro'
    private Libro libro;

    @Column(name = "fecha_reserva_libro")
    // Fecha en la que se hizo la reserva del libro
    private LocalDate fechaReservaLibro;

    @Column(name = "fecha_devolucion")
    // Fecha en la que se espera (o se hizo) la devolución
    private LocalDate fechaDevolucion;

    // Campo adicional para almacenar la URL o path de una imagen asociada (opcional)
    private String imagen;

    // Constructor por defecto (requerido por JPA)
    public UsuarioLibro() {}

    // Constructor completo (útil para crear instancias manualmente)
    public UsuarioLibro(
        Long idReservaLibro,
        Usuario usuario,
        Libro libro,
        LocalDate fechaReservaLibro,
        LocalDate fechaDevolucion,
        String imagen
    ) {
        this.idReservaLibro = idReservaLibro;
        this.usuario = usuario;
        this.libro = libro;
        this.fechaReservaLibro = fechaReservaLibro;
        this.fechaDevolucion = fechaDevolucion;
        this.imagen = imagen;
    }

    // Getters y Setters

    public Long getIdReservaLibro() {
        return idReservaLibro;
    }

    public void setIdReservaLibro(Long idReservaLibro) {
        this.idReservaLibro = idReservaLibro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDate getFechaReservaLibro() {
        return fechaReservaLibro;
    }

    public void setFechaReservaLibro(LocalDate fechaReservaLibro) {
        this.fechaReservaLibro = fechaReservaLibro;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

