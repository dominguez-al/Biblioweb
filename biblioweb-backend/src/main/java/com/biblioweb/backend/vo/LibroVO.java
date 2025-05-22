package com.biblioweb.backend.vo;

/**
 * VO (Value Object) que representa los datos visibles de un libro.
 * Es usado para exponer únicamente los atributos necesarios al frontend
 * y evitar exponer detalles sensibles o relaciones innecesarias de la entidad real.
 */
public class LibroVO {

    // ID único del libro
    private Long idLibro;

    // Título del libro
    private String titulo;

    // Autor del libro
    private String autor;

    // Género literario (ej: ciencia ficción, novela, etc.)
    private String genero;

    // Estado actual del libro (ej: "DISPONIBLE", "RESERVADO")
    private String estado;

    // Ruta o nombre del archivo de imagen del libro
    private String imagen;

    // Fecha en que el libro fue dado de alta en el sistema (formato string para compatibilidad frontend)
    private String fechaAltaLibro;

    // Nombre o email del usuario que reservó el libro (si está reservado)
    private String reservadoPor;

    // Número total de veces que este libro ha sido reservado
    private int totalReservas;

    /**
     * Constructor vacío necesario para frameworks como Jackson (serialización/deserialización).
     */
    public LibroVO() {}

    /**
     * Constructor completo para inicializar todos los atributos del libro.
     * Útil al mapear desde la entidad o en tests.
     */
    public LibroVO(Long idLibro, String titulo, String autor, String genero, String estado, String imagen, String fechaAltaLibro, String reservadoPor, int totalReservas) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.estado = estado;
        this.imagen = imagen;
        this.fechaAltaLibro = fechaAltaLibro;
        this.reservadoPor = reservadoPor;
        this.totalReservas = totalReservas;
    }

    // --- Getters y Setters necesarios para acceder y modificar los datos desde el frontend o mappers ---

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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFechaAltaLibro() {
        return fechaAltaLibro;
    }

    public void setFechaAltaLibro(String fechaAltaLibro) {
        this.fechaAltaLibro = fechaAltaLibro;
    }

    public String getReservadoPor() {
        return reservadoPor;
    }

    public void setReservadoPor(String reservadoPor) {
        this.reservadoPor = reservadoPor;
    }

    public int getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(int totalReservas) {
        this.totalReservas = totalReservas;
    }
}
