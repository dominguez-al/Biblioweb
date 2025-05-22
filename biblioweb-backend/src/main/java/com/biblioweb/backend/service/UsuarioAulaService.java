package com.biblioweb.backend.service;

import com.biblioweb.backend.entity.UsuarioAula;
import com.biblioweb.backend.vo.UsuarioAulaVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones para la gestión de reservas de aulas por parte de usuarios.
 * Sirve como contrato para la implementación que maneja la lógica de negocio.
 */
public interface UsuarioAulaService {

    /**
     * Lista todas las reservas de aulas registradas en el sistema.
     * Útil para administración o monitoreo general.
     *
     * @return lista de reservas (entidades)
     */
    List<UsuarioAula> listarReservas();

    /**
     * Busca una reserva específica por su ID.
     *
     * @param id ID de la reserva
     * @return Optional que puede contener la reserva encontrada
     */
    Optional<UsuarioAula> obtenerReservaPorId(Long id);

    /**
     * Guarda una reserva directamente (sin lógica de validación adicional).
     * Útil para cargas masivas o pruebas.
     *
     * @param reserva objeto reserva a guardar
     * @return reserva guardada
     */
    UsuarioAula guardarReserva(UsuarioAula reserva);

    /**
     * Elimina una reserva existente por su ID.
     * Generalmente incluye validaciones de autorización en la implementación.
     *
     * @param id ID de la reserva a eliminar
     */
    void eliminarReserva(Long id);

    /**
     * Crea una nueva reserva, validando previamente la disponibilidad del aula en la fecha indicada.
     *
     * @param reserva objeto a reservar
     * @return reserva creada si es válida
     */
    UsuarioAula crearReserva(UsuarioAula reserva);

    /**
     * Devuelve todas las reservas activas de un usuario específico.
     *
     * @param idUsuario ID del usuario
     * @return lista de reservas asociadas al usuario
     */
    List<UsuarioAula> obtenerReservasPorUsuario(Long idUsuario);

    /**
     * Crea una reserva a partir de un VO recibido desde el cliente.
     * Este método encapsula la conversión de VO a entidad y la lógica de negocio necesaria.
     *
     * @param vo Objeto de vista que contiene los datos de la reserva
     * @return reserva creada
     */
    UsuarioAula crearDesdeVO(UsuarioAulaVO vo);

    /**
     * Devuelve todas las fechas ocupadas para un aula específica.
     * Útil para deshabilitar fechas en un calendario del frontend.
     *
     * @param idAula ID del aula
     * @return lista de fechas que ya están reservadas
     */
    List<LocalDate> obtenerFechasOcupadas(Long idAula);
}

