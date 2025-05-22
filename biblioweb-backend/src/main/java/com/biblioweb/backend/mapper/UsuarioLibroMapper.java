package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Libro;
import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.entity.UsuarioLibro;
import com.biblioweb.backend.vo.UsuarioLibroVO;

/**
 * Mapper para convertir entre UsuarioLibro (entidad JPA) y UsuarioLibroVO (objeto de vista).
 * Se usa para separar la lógica de base de datos del modelo que se expone al cliente.
 */
public class UsuarioLibroMapper {

    /**
     * Convierte una entidad UsuarioLibro en un objeto UsuarioLibroVO.
     * Este método se usa para mostrar datos al cliente.
     *
     * @param entity La entidad que representa la reserva de un libro por un usuario
     * @return UsuarioLibroVO con datos relevantes para el frontend
     */
    public static UsuarioLibroVO toVO(UsuarioLibro entity) {
        UsuarioLibroVO vo = new UsuarioLibroVO();
        vo.setId(entity.getIdReservaLibro()); 						// ID de la reserva
        vo.setIdUsuario(entity.getUsuario().getIdUsuario()); 		// ID del usuario
        vo.setIdLibro(entity.getLibro().getIdLibro()); 				// ID del libro
        vo.setFechaReservaLibro(entity.getFechaReservaLibro()); 	// Fecha de reserva
        vo.setFechaDevolucion(entity.getFechaDevolucion()); 		// Fecha de devolución

        // Información adicional útil para la vista
        vo.setImagen(entity.getLibro().getImagen()); 				// Imagen del libro (portada)
        vo.setTitulo(entity.getLibro().getTitulo()); 				// Título del libro

        return vo;
    }

    /**
     * Convierte un objeto UsuarioLibroVO en una entidad UsuarioLibro.
     * Se usa para crear o actualizar reservas desde el frontend.
     *
     * @param vo      Objeto VO recibido del cliente
     * @param usuario Entidad Usuario (ya recuperada por su ID)
     * @param libro   Entidad Libro (ya recuperada por su ID)
     * @return Entidad UsuarioLibro lista para ser persistida
     */
    public static UsuarioLibro toEntity(UsuarioLibroVO vo, Usuario usuario, Libro libro) {
        UsuarioLibro entity = new UsuarioLibro();
        entity.setIdReservaLibro(vo.getId()); 							// Solo necesario si es una actualización
        entity.setUsuario(usuario); 									// Asocia al usuario correspondiente
        entity.setLibro(libro); 										// Asocia al libro correspondiente
        entity.setFechaReservaLibro(vo.getFechaReservaLibro());
        entity.setFechaDevolucion(vo.getFechaDevolucion());
        return entity;
    }
}
