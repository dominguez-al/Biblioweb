package com.biblioweb.backend.vo;

import java.time.LocalDate;

/**
 * VO (Value Object) que representa una reserva de aula por parte de un usuario.
 * Se usa para enviar y recibir datos desde el frontend, evitando exponer directamente
 * la entidad JPA UsuarioAula y sus relaciones.
 */
public class UsuarioAulaVO {

    // ID único de la reserva
    private Long id;

    // ID del usuario que hizo la reserva
    private Long idUsuario;

    // ID del aula que fue reservada
    private Long idAula;

    // Fecha de la reserva (día en que se utiliza el aula)
    private LocalDate fechaReservaAula;

    // Fecha de devolución o finalización de uso (en este caso, misma que la reserva)
    private LocalDate fechaDevolucionAula;

    // Nombre del aula reservada (útil para mostrar en el frontend sin más consultas)
    private String nombreAula;

    // Email o nombre del usuario que realizó la reserva
    private String reservadoPor;

    // Constructor vacío requerido por frameworks como Jackson
    public UsuarioAulaVO() {}

    /**
     * Constructor completo con todos los campos.
     * Útil para retornar objetos desde el backend hacia el frontend ya preparados.
     */
    public UsuarioAulaVO(
        Long id,
        Long idUsuario,
        Long idAula,
        LocalDate fechaReservaAula,
        LocalDate fechaDevolucionAula,
        String nombreAula,
        String reservadoPor
    ) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idAula = idAula;
        this.fechaReservaAula = fechaReservaAula;
        this.fechaDevolucionAula = fechaDevolucionAula;
        this.nombreAula = nombreAula;
        this.reservadoPor = reservadoPor;
    }

    // --- Getters y Setters para todos los campos ---

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

    public Long getIdAula() {
        return idAula;
    }

    public void setIdAula(Long idAula) {
        this.idAula = idAula;
    }

    public LocalDate getFechaReservaAula() {
        return fechaReservaAula;
    }

    public void setFechaReservaAula(LocalDate fechaReservaAula) {
        this.fechaReservaAula = fechaReservaAula;
    }

    public LocalDate getFechaDevolucionAula() {
        return fechaDevolucionAula;
    }

    public void setFechaDevolucionAula(LocalDate fechaDevolucionAula) {
        this.fechaDevolucionAula = fechaDevolucionAula;
    }

    public String getNombreAula() {
        return nombreAula;
    }

    public void setNombreAula(String nombreAula) {
        this.nombreAula = nombreAula;
    }

    public String getReservadoPor() {
        return reservadoPor;
    }

    public void setReservadoPor(String reservadoPor) {
        this.reservadoPor = reservadoPor;
    }
}
