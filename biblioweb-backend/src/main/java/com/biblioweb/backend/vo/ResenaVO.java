package com.biblioweb.backend.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ResenaVO {

    private Long id;
    private String comentario;
    private int puntuacion;
    private LocalDateTime fecha;
    private String usuarioId;
    private Long libroId;
    private String tituloLibro;

    
    //Constructores
    
    public ResenaVO() {
    }
    
	public ResenaVO(Long id, String comentario, int puntuacion, LocalDateTime fecha, String usuarioId, Long libroId, String tituloLibro) {
		this.id = id;
		this.comentario = comentario;
		this.puntuacion = puntuacion;
		this.fecha = fecha;
		this.usuarioId = usuarioId;
		this.libroId = libroId;
		this.tituloLibro = tituloLibro;
	}

	 //Setters y Getters
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getComentario() {
		return comentario;
	}


	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	public int getPuntuacion() {
		return puntuacion;
	}


	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}


	public LocalDateTime getFecha() {
		return fecha;
	}


	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}


	public String getUsuarioId() {
		return usuarioId;
	}


	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}


	public Long getLibroId() {
		return libroId;
	}


	public void setLibroId(Long libroId) {
		this.libroId = libroId;
	}

	public String getTituloLibro() {
		return tituloLibro;
	}

	public void setTituloLibro(String tituloLibro) {
		this.tituloLibro = tituloLibro;
	}

	
	
}