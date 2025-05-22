package com.biblioweb.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // Marca esta clase como una entidad de JPA
@Table(name = "resena") // Asocia esta entidad con la tabla 'resena' en la base de datos
public class Resena {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Valor generado automáticamente (auto-incremental)
    private Long id;

    private String comentario; // Texto de la reseña escrita por el usuario

    private int puntuacion; // Puntuación del libro 

    private LocalDateTime fecha; // Fecha y hora en que se publicó la reseña

    @JoinColumn(name = "usuario_id")
    private String usuarioId; // Identificador del usuario que escribió la reseña (puede ser un email, UUID, etc.)

    // Relación muchos-a-uno: muchas reseñas pueden estar asociadas a un solo libro
    @ManyToOne
    @JoinColumn(name = "libro_id") // Define el nombre de la columna que actúa como clave foránea
    private Libro libro;

    // Constructor vacío necesario para JPA
    public Resena() {}

    // Constructor completo
    public Resena(Long id, String comentario, int puntuacion, LocalDateTime fecha, String usuarioId, Libro libro) {
        this.id = id;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.libro = libro;
    }

    // Getters y Setters

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
