package com.biblioweb.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "resena")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentario; // Texto de la reseña

    private int puntuacion; // Valoración de 1 a 5

    private LocalDateTime fecha; // Fecha de la reseña

    private String usuarioId; // Usuario que la escribió

    @ManyToOne // Muchas reseñas pueden pertenecer a un solo libro
    @JoinColumn(name = "libro_id") // Clave foránea en la tabla
    private Libro libro;

    
    //Contructores
    public Resena() {
	}

    
    public Resena(Long id, String comentario, int puntuacion, LocalDateTime fecha, String usuarioId, Libro libro) {
		this.id = id;
		this.comentario = comentario;
		this.puntuacion = puntuacion;
		this.fecha = fecha;
		this.usuarioId = usuarioId;
		this.libro = libro;
	}
    

	// Getters y setters (puedes generarlos con tu IDE)
    public Long getId() { return id; }
    
	public void setId(Long id) { this.id = id; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }
}

