package com.biblioweb.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity // Declara esta clase como una entidad JPA
@Table(name = "usuario_aula") // La entidad se mapea a la tabla 'usuario_aula'
public class UsuarioAula {

    @Id // Clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental generado por la BD
    @Column(name = "id_reserva_aula")
    private Long idReservaAula;

    // Relación muchos-a-uno con Usuario: varios registros pueden pertenecer a un mismo usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario") // Define la clave foránea hacia la tabla 'usuario'
    private Usuario usuario;

    // Relación muchos-a-uno con Aula: un aula puede estar reservada muchas veces
    @ManyToOne
    @JoinColumn(name = "id_aula") // Define la clave foránea hacia la tabla 'aula'
    private Aula aula;

    // Fecha en la que se reserva el aula
    @Column(name = "fecha_reserva_aula")
    private LocalDate fechaReservaAula;

    // Fecha prevista o real de devolución del aula
    @Column(name = "fecha_devolucion_aula")
    private LocalDate fechaDevolucionAula;

    // Constructor por defecto 
    public UsuarioAula() {}

    // Constructor completo (
    public UsuarioAula(
        Long idReservaAula,
        Usuario usuario,
        Aula aula,
        LocalDate fechaReservaAula,
        LocalDate fechaDevolucionAula
    ) {
        this.idReservaAula = idReservaAula;
        this.usuario = usuario;
        this.aula = aula;
        this.fechaReservaAula = fechaReservaAula;
        this.fechaDevolucionAula = fechaDevolucionAula;
    }

    // Getters y Setters

    public Long getIdReservaAula() {
        return idReservaAula;
    }

    public void setIdReservaAula(Long idReservaAula) {
        this.idReservaAula = idReservaAula;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
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
}

