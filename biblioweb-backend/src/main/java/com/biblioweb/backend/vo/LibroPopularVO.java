package com.biblioweb.backend.vo;

public class LibroPopularVO {
    private Long idLibro;
    private String titulo;
    private String imagen;
    private Long totalReservas;

    public LibroPopularVO(Long idLibro, String titulo, String imagen, Long totalReservas) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.imagen = imagen;
        this.totalReservas = totalReservas;
    }

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
