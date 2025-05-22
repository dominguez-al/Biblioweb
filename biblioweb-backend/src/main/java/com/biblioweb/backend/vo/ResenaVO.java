package com.biblioweb.backend.vo;

import java.time.LocalDateTime;

/**
 * VO (Value Object) que representa los datos visibles de una reseña.
 * Este objeto se usa para enviar y recibir información de reseñas
 * entre el backend y el frontend, sin exponer directamente la entidad JPA.
 */
public class ResenaVO {

    // Identificador único de la reseña
    private Long id;

    // Texto del comentario que dejó el usuario
    private String comentario;

    // Valoración numérica (por ejemplo, entre 1 y 5)
    private int puntuacion;

    // Fecha y hora en la que se hizo la reseña
    private LocalDateTime fecha;

    // ID del usuario que dejó la reseña (puede ser email, nombre de usuario o UUID, según implementación)
    private String usuarioId;

    // ID del libro al que pertenece esta reseña
    private Long libroId;

    // Título del libro reseñado (útil para mostrar en listas sin hacer JOIN adicionales en frontend)
    private String tituloLibro;

    // Constructor vacío requerido por frameworks de serialización como Jackson
    public ResenaVO() {
    }

    /**
     * Constructor completo, útil para crear objetos ResenaVO desde la capa de servicio o mapper.
     */
    public ResenaVO(Long id, String comentario, int puntuacion, LocalDateTime fecha, String usuarioId, Long libroId, String tituloLibro) {
        this.id = id;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.libroId = libroId;
        this.tituloLibro = tituloLibro;
    }

    // --- Getters y Setters necesarios para lectura y escritura de los campos ---

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
