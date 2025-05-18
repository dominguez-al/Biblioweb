package com.biblioweb.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    private String titulo;
    private String autor;
    private String genero;
    private String estado;
    private LocalDate fechaAltaLibro;
    private String imagen;
    private int totalReservas;


    @OneToMany(mappedBy = "libro", fetch = FetchType.LAZY) // Un libro puede tener muchas reservas
    private List<UsuarioLibro> reservas = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true) //Hacer reseñas de los libros
    private List<Resena> resenas;

    
    @PrePersist
    protected void onCreate() {
        this.fechaAltaLibro = LocalDate.now(); // Asigna automáticamente la fecha al crear
    }

    public Libro() {}

    public Libro(Long idLibro, String titulo, String autor, String genero, String estado, String imagen, int totalReservas) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.estado = estado;
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

