package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Aula;
import com.biblioweb.backend.vo.AulaVO;

/**
 * Mapper para convertir entre la entidad Aula y su representación de vista (VO - Value Object).
 * Esta clase permite desacoplar la capa de datos de la capa de presentación.
 */
public class AulaMapper {

    /**
     * Convierte una entidad Aula a un AulaVO para exponer datos al cliente.
     * 
     * @param aula la entidad que se quiere transformar
     * @return un objeto AulaVO con los datos del aula
     */
    public static AulaVO toVO(Aula aula) {
        AulaVO vo = new AulaVO();
        vo.setId(aula.getIdAula());
        vo.setNombre(aula.getNombre());
        vo.setCapacidad(aula.getCapacidad());
        vo.setEstado(aula.getEstado());
        return vo;
    }

    /**
     * Convierte un AulaVO recibido del cliente a una entidad Aula.
     * Se usa cuando se va a crear o actualizar una entidad en la base de datos.
     * 
     * @param vo el objeto de vista recibido del frontend
     * @return una entidad Aula lista para guardar en la BD
     */
    public static Aula toEntity(AulaVO vo) {
        Aula aula = new Aula();
        aula.setIdAula(vo.getId()); // Este ID se usa solo en actualizaciones
        aula.setNombre(vo.getNombre());
        aula.setCapacidad(vo.getCapacidad());
        aula.setEstado(vo.getEstado());
        return aula;
    }
}
