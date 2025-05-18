package com.biblioweb.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario_libro")
public class UsuarioLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservaLibro;

    @ManyToOne
    @JoinColumn(name = "id_usuario") // FK a Usuario
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_libro") // FK a Libro
    private Libro libro;

    private LocalDate fechaReservaLibro;
    private LocalDate fechaDevolucion;
    private String imagen;

    public UsuarioLibro() {}

    // Constructor personalizado
    public UsuarioLibro(Long idReservaLibro, Usuario usuario, Libro libro, LocalDate fechaReservaLibro, LocalDate fechaDevolucion, String imagen) {
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
