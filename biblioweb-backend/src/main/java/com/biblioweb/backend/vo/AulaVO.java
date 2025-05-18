package com.biblioweb.backend.vo;

/**
 * VO que representa los datos visibles de un aula
 */
public class AulaVO {

    private Long id;
    private String nombre;
    private Integer capacidad;
    private String estado;

    public AulaVO() {}

    public AulaVO(Long id, String nombre, Integer capacidad, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    // Getters y setters
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

