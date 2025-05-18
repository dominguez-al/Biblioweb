package com.biblioweb.backend.entity;

import jakarta.persistence.*; // Anotaciones de JPA para definir entidades y relaciones
import java.util.List;

@Entity // Declara esta clase como una entidad JPA (corresponde a una tabla en BD)
@Table(name = "aula") // Asocia esta clase con la tabla 'aula' en la BD
public class Aula {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-generado (incremental)
    private Long idAula;

    private String nombre;
    private Integer capacidad;
    private String estado;


    @OneToMany(mappedBy = "aula") // Una aula puede tener muchas reservas (UsuarioAula)
    private List<UsuarioAula> reservas;

    public Aula() {}

    // Constructor personalizado
    public Aula(Long idAula, String nombre, Integer capacidad, String estado) {
        this.idAula = idAula;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getIdAula() {
        return idAula;
    }

    public void setIdAula(Long idAula) {
        this.idAula = idAula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public List<UsuarioAula> getReservas() {
        return reservas;
    }

    public void setReservas(List<UsuarioAula> reservas) {
        this.reservas = reservas;
    }

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
    
    
    
    
}
