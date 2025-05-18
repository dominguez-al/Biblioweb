package com.biblioweb.backend.vo;

/**
 * VO que representa los datos visibles de un libro
 */
public class LibroVO {

    private Long idLibro;
    private String titulo;
    private String autor;
    private String genero;
    private String estado;
    private String imagen;
    private String fechaAltaLibro;
    private String reservadoPor;
    private int totalReservas;

    public LibroVO() {}

    public LibroVO(Long idLibro, String titulo, String autor, String genero, String estado, String imagen, String fechaAltaLibro, String reservadoPor) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.estado = estado;
        this.imagen = imagen;
        this.fechaAltaLibro = fechaAltaLibro;
        this.reservadoPor = reservadoPor;
        this.totalReservas = totalReservas;
    }

    // Getters y setters
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

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getFechaAltaLibro() {
		return fechaAltaLibro;
	}

	public void setFechaAltaLibro(String fechaAltaLibro) {
		this.fechaAltaLibro = fechaAltaLibro;
	}
    
	public String getReservadoPor() {
	    return reservadoPor;
	}

	public void setReservadoPor(String reservadoPor) {
	    this.reservadoPor = reservadoPor;
	}
	
	public int getTotalReservas() {
	    return totalReservas;
	}

	public void setTotalReservas(int totalReservas) {
	    this.totalReservas = totalReservas;
	}
    
}

