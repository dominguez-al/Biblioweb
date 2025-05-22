package com.biblioweb.backend.entity;

import jakarta.persistence.*; // Anotaciones de JPA para entidades, relaciones, claves primarias, etc.
import java.util.List;

@Entity // Marca esta clase como una entidad persistente (se mapea a una tabla)
@Table(name = "aula") // Especifica que la entidad se asocia con la tabla 'aula' en la base de datos
public class Aula {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El valor se genera automáticamente (auto-incremental)
    @Column(name = "id_aula")
    private Long idAula;

    // Nombre del aula
    private String nombre;

    // Capacidad máxima de personas en el aula
    private Integer capacidad;

    // Estado del aula (Ej: "DISPONIBLE", "EN_MANTENIMIENTO")
    private String estado;

    // Relación uno-a-muchos: un aula puede tener muchas reservas
    @OneToMany(mappedBy = "aula") // 'aula' es el nombre del atributo en la clase UsuarioAula que mapea esta relación
    private List<UsuarioAula> reservas;

    // Constructor por defecto 
    public Aula() {}

    // Constructor completo 
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<UsuarioAula> getReservas() {
        return reservas;
    }

    public void setReservas(List<UsuarioAula> reservas) {
        this.reservas = reservas;
    }
}

