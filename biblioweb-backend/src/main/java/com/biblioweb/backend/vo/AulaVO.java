package com.biblioweb.backend.vo;

/**
 * VO (Value Object) que representa los datos visibles de un Aula.
 * Se utiliza para enviar y recibir datos de aulas en el frontend,
 * evitando exponer relaciones o lógica innecesaria del modelo de entidad.
 */
public class AulaVO {

    // Identificador único del aula
    private Long id;

    // Nombre o etiqueta del aula (Ej: Aula 1, Sala de informática)
    private String nombre;

    // Capacidad máxima de personas del aula
    private Integer capacidad;

    // Estado actual del aula (Ej: "disponible", "ocupado")
    private String estado;

    // Constructor vacío requerido por frameworks (como Jackson)
    public AulaVO() {}

    // Constructor completo para facilitar instancias desde el backend
    public AulaVO(Long id, String nombre, Integer capacidad, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    // Getters y Setters necesarios para serialización/deserialización

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}

