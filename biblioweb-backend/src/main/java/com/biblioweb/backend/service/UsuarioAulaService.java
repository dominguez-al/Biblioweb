package com.biblioweb.backend.service;

import com.biblioweb.backend.entity.UsuarioAula;
import com.biblioweb.backend.vo.UsuarioAulaVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones para la gestión de reservas de aulas por usuarios.
 */
public interface UsuarioAulaService {

    /**
     * Lista todas las reservas existentes.
     */
    List<UsuarioAula> listarReservas();

    /**
     * Busca una reserva específica por su ID.
     */
    Optional<UsuarioAula> obtenerReservaPorId(Long id);

    /**
     * Guarda una nueva reserva (sin validaciones adicionales).
     */
    UsuarioAula guardarReserva(UsuarioAula reserva);

    /**
     * Elimina una reserva existente por su ID y notifica al usuario.
     */
    void eliminarReserva(Long id);

    /**
     * Crea una nueva reserva con validación de disponibilidad.
     */
    UsuarioAula crearReserva(UsuarioAula reserva);
    
    /**
     * Crea una nueva reserva con validación de disponibilidad.
     */
    
    List<UsuarioAula> obtenerReservasPorUsuario(Long idUsuario);
    
    
    
    UsuarioAula crearDesdeVO(UsuarioAulaVO vo);
    
    List<LocalDate> obtenerFechasOcupadas(Long idAula);



}

