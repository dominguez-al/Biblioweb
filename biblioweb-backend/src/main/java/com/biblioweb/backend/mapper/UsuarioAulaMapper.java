package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Aula;
import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.entity.UsuarioAula;
import com.biblioweb.backend.vo.UsuarioAulaVO;

/**
 * Mapper para convertir entre UsuarioAula (entidad) y UsuarioAulaVO (objeto de vista).
 * Facilita la separación entre la lógica de negocio y la interfaz de usuario.
 */
public class UsuarioAulaMapper {

    /**
     * Convierte una entidad UsuarioAula en su representación VO para enviar al cliente.
     *
     * @param entity La entidad que representa una reserva de aula
     * @return UsuarioAulaVO con los datos necesarios para el frontend
     */
    public static UsuarioAulaVO toVO(UsuarioAula entity) {
        UsuarioAulaVO vo = new UsuarioAulaVO();
        vo.setId(entity.getIdReservaAula()); 						// ID de la reserva
        vo.setIdUsuario(entity.getUsuario().getIdUsuario()); 		// ID del usuario
        vo.setIdAula(entity.getAula().getIdAula()); 				// ID del aula
        vo.setFechaReservaAula(entity.getFechaReservaAula()); 		// Fecha de inicio de la reserva
        vo.setFechaDevolucionAula(entity.getFechaDevolucionAula()); // Fecha de devolución

        // Datos adicionales útiles para mostrar en el frontend
        vo.setNombreAula(entity.getAula().getNombre()); 			// Nombre del aula
        vo.setReservadoPor(entity.getUsuario().getEmail()); 		// Email del usuario que hizo la reserva

        return vo;
    }

    /**
     * Convierte un VO recibido del cliente en una entidad UsuarioAula.
     * Se usa para crear o actualizar reservas.
     *
     * @param vo      El VO recibido desde el frontend
     * @param usuario El objeto Usuario previamente recuperado (por ID)
     * @param aula    El objeto Aula previamente recuperado (por ID)
     * @return Entidad UsuarioAula lista para persistencia
     */
    public static UsuarioAula toEntity(UsuarioAulaVO vo, Usuario usuario, Aula aula) {
        UsuarioAula entity = new UsuarioAula();
        entity.setIdReservaAula(vo.getId()); 					// En actualizaciones, se conserva el ID
        entity.setUsuario(usuario); 							// Asigna la entidad usuario
        entity.setAula(aula); 									// Asigna la entidad aula
        entity.setFechaReservaAula(vo.getFechaReservaAula());
        entity.setFechaDevolucionAula(vo.getFechaDevolucionAula());
        return entity;
    }
}
