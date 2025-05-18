package com.biblioweb.backend.service;

import com.biblioweb.backend.entity.UsuarioLibro;
import com.biblioweb.backend.vo.LibroPopularVO;
import com.biblioweb.backend.vo.UsuarioLibroVO;

import java.util.List;

/**
 * Interfaz que define las operaciones relacionadas con la reserva de libros por usuarios.
 */
public interface UsuarioLibroService {

    /**
     * Lista todas las reservas de libros existentes.
     * @return lista de reservas
     */
    List<UsuarioLibro> listarReservas();
    
    // UsuarioLibroService.java
    List<UsuarioLibro> obtenerReservasPorUsuario(Long idUsuario);


    /**
     * Crea una reserva de libro a partir de un VO (vista).
     * @param vo objeto de vista del usuario-libro
     * @return entidad creada
     */
    UsuarioLibro crearDesdeVO(UsuarioLibroVO vo);

    /**
     * Elimina una reserva de libro por su ID.
     * @param id identificador de la reserva
     */
    void eliminarReserva(Long id);
    


}

