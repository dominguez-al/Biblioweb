package com.biblioweb.backend.vo;

import java.time.LocalDate;

public class UsuarioAulaVO {
    private Long id;
    private Long idUsuario;
    private Long idAula;
    private LocalDate fechaReservaAula;
    private LocalDate fechaDevolucionAula;
    private String nombreAula;
    private String reservadoPor;

    public UsuarioAulaVO() {}

    public UsuarioAulaVO(Long id, Long idUsuario, Long idAula, LocalDate fechaReservaAula, LocalDate fechaDevolucionAula, String nombreAula, String reservadoPor) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idAula = idAula;
        this.fechaReservaAula = fechaReservaAula;
        this.fechaDevolucionAula = fechaDevolucionAula;
        this.nombreAula = nombreAula;
        this.reservadoPor = reservadoPor;
    }

    // Getters y setters...
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdAula() {
		return idAula;
	}

	public void setIdAula(Long idAula) {
		this.idAula = idAula;
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

	public String getNombreAula() {
		return nombreAula;
	}

	public void setNombreAula(String nombreAula) {
		this.nombreAula = nombreAula;
	}

	public String getReservadoPor() {
		return reservadoPor;
	}

	public void setReservadoPor(String reservadoPor) {
		this.reservadoPor = reservadoPor;
	}

	

    
}
