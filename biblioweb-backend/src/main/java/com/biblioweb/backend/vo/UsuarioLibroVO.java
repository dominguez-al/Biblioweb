package com.biblioweb.backend.vo;

import java.time.LocalDate;

/**
 * VO que representa una reserva de libro realizada por un usuario
 */
public class UsuarioLibroVO {

    private Long id;
    private Long idUsuario;
    private Long idLibro;
    private LocalDate fechaReservaLibro;
    private LocalDate fechaDevolucion;
    private String imagen;
    private String titulo;


    public UsuarioLibroVO() {}

    public UsuarioLibroVO(Long id, Long idUsuario, Long idLibro, LocalDate fechaReservaLibro, LocalDate fechaDevolucion, String imagen, String titulo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaReservaLibro = fechaReservaLibro;
        this.fechaDevolucion = fechaDevolucion;
        this.imagen = imagen;
        this.titulo = titulo;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
    
}

