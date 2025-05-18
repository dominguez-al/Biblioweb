package com.biblioweb.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario_aula")
public class UsuarioAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservaAula;

    @ManyToOne
    @JoinColumn(name = "id_usuario") // FK a Usuario
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_aula") // FK a Aula
    private Aula aula;

    private LocalDate fechaReservaAula;
    private LocalDate fechaDevolucionAula;

    public UsuarioAula() {}

    // Constructor personalizado
    public UsuarioAula(Long idReservaAula, Usuario usuario, Aula aula, LocalDate fechaReservaAula, LocalDate fechaDevolucionAula) {
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
