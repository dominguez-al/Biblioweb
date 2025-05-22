package com.biblioweb.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity // Marca esta clase como una entidad de JPA 
@Table(name = "libro") // Asocia esta clase con la tabla 'libro' en la base de datos
public class Libro {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental 
    @Column(name = "id_libro")
    private Long idLibro;

    // Atributos principales del libro
    private String titulo;
    private String autor;
    private String genero;
    private String estado; // Ej: "DISPONIBLE", "PRESTADO", "INACTIVO"
    @Column(name = "fecha_alta_libro")
    private LocalDate fechaAltaLibro; // Fecha de alta 
    private String imagen; // URL o path de la imagen asociada
    @Column(name = "total_reservas")
    private int totalReservas; // Contador de reservas hechas 

    // Relación uno-a-muchos: un libro puede tener muchas reservas
    @OneToMany(mappedBy = "libro", fetch = FetchType.LAZY) // Carga perezosa para optimizar el rendimiento
    private List<UsuarioLibro> reservas = new java.util.ArrayList<>();

    // Relación uno-a-muchos: un libro puede tener muchas reseñas
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas;

    // Asigna automáticamente la fecha de alta cuando se guarda el libro por primera vez
    @PrePersist
    protected void onCreate() {
        this.fechaAltaLibro = LocalDate.now();
    }

    // Constructor por defecto requerido por JPA
    public Libro() {}

    // Constructor personalizado (sin lista de reservas ni reseñas)
    public Libro(Long idLibro, String titulo, String autor, String genero, String estado, String imagen, int totalReservas) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.estado = estado;
        this.imagen = imagen;
        this.totalReservas = totalReservas;
    }

    // Getters y Setters (acceso a los campos privados)

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

    public LocalDate getFechaAltaLibro() {
        return fechaAltaLibro;
    }

    public void setFechaAltaLibro(LocalDate fechaAltaLibro) {
        this.fechaAltaLibro = fechaAltaLibro;
    }

    public List<UsuarioLibro> getReservas() {
        return reservas;
    }

    public void setReservas(List<UsuarioLibro> reservas) {
        this.reservas = reservas;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Resena> getResenas() {
        return resenas;
    }

    public void setResenas(List<Resena> resenas) {
        this.resenas = resenas;
    }

    public int getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(int totalReservas) {
        this.totalReservas = totalReservas;
    }
}
