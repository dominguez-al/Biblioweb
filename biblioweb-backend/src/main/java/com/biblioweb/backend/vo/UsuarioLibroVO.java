package com.biblioweb.backend.vo;

import java.time.LocalDate;

/**
 * VO (Value Object) que representa una reserva de libro realizada por un usuario.
 * Se utiliza para enviar y recibir datos desde o hacia el frontend,
 * evitando exponer directamente la entidad JPA UsuarioLibro y sus relaciones.
 */
public class UsuarioLibroVO {

    // ID único de la reserva
    private Long id;

    // ID del usuario que hizo la reserva
    private Long idUsuario;

    // ID del libro reservado
    private Long idLibro;

    // Fecha en la que se realizó la reserva
    private LocalDate fechaReservaLibro;

    // Fecha en la que se espera la devolución del libro
    private LocalDate fechaDevolucion;

    // Imagen del libro (ruta o nombre del archivo), útil para mostrar en listas
    private String imagen;

    // Título del libro reservado, para mostrar sin tener que consultar la entidad Libro
    private String titulo;

    /**
     * Constructor vacío necesario para la serialización (por ejemplo, con Jackson).
     */
    public UsuarioLibroVO() {}

    /**
     * Constructor completo para inicializar todos los campos del VO.
     * Se puede usar desde el mapper o al retornar información al frontend.
     */
    public UsuarioLibroVO(
        Long id,
        Long idUsuario,
        Long idLibro,
        LocalDate fechaReservaLibro,
        LocalDate fechaDevolucion,
        String imagen,
        String titulo
    ) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaReservaLibro = fechaReservaLibro;
        this.fechaDevolucion = fechaDevolucion;
        this.imagen = imagen;
        this.titulo = titulo;
    }

    // --- Getters y Setters ---

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
