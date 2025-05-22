package com.biblioweb.backend.service;

import com.biblioweb.backend.entity.UsuarioLibro;
import com.biblioweb.backend.vo.UsuarioLibroVO;

import java.util.List;

/**
 * Interfaz que define las operaciones relacionadas con la reserva de libros por parte de usuarios.
 * Establece el contrato que debe cumplir cualquier implementación del servicio.
 */
public interface UsuarioLibroService {

    /**
     * Lista todas las reservas de libros existentes en el sistema.
     * Usado generalmente por administradores o para reportes.
     *
     * @return lista completa de reservas de libros
     */
    List<UsuarioLibro> listarReservas();

    /**
     * Devuelve todas las reservas de libros asociadas a un usuario específico.
     * Permite a los usuarios ver su historial o sus reservas activas.
     *
     * @param idUsuario ID del usuario
     * @return lista de reservas correspondientes al usuario
     */
    List<UsuarioLibro> obtenerReservasPorUsuario(Long idUsuario);

    /**
     * Crea una nueva reserva de libro a partir de un VO (objeto de vista).
     * Este método se encarga de validar, convertir el VO en entidad y persistir la reserva.
     *
     * @param vo objeto que representa los datos enviados desde el frontend
     * @return entidad `UsuarioLibro` creada y guardada
     */
    UsuarioLibro crearDesdeVO(UsuarioLibroVO vo);

    /**
     * Elimina una reserva de libro identificada por su ID.
     * Suele incluir verificaciones de permisos en la implementación.
     *
     * @param id ID de la reserva que se desea eliminar
     */
    void eliminarReserva(Long id);
}

