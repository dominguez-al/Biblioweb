package com.biblioweb.backend.vo;

/**
 * VO (Value Object) que representa un resumen de un libro popular,
 * usado para mostrar en rankings o listados destacados.
 */
public class LibroPopularVO {

    // Identificador único del libro
    private Long idLibro;

    // Título del libro
    private String titulo;

    // URL o nombre del archivo de imagen asociado al libro
    private String imagen;

    // Número total de veces que ha sido reservado (popularidad)
    private Long totalReservas;

    /**
     * Constructor completo para inicializar todas las propiedades.
     * Se suele usar al mapear consultas personalizadas.
     */
    public LibroPopularVO(Long idLibro, String titulo, String imagen, Long totalReservas) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.imagen = imagen;
        this.totalReservas = totalReservas;
    }

    // Getters y Setters

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Long getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(Long totalReservas) {
        this.totalReservas = totalReservas;
    }
}
