package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Aula;
import com.biblioweb.backend.vo.AulaVO;

/**
 * Mapper para convertir entre Aula y AulaVO
 */
public class AulaMapper {

    public static AulaVO toVO(Aula aula) {
        AulaVO vo = new AulaVO();
        vo.setId(aula.getIdAula());
        vo.setNombre(aula.getNombre());
        vo.setCapacidad(aula.getCapacidad());
        vo.setEstado(aula.getEstado());
        return vo;
    }

    public static Aula toEntity(AulaVO vo) {
        Aula aula = new Aula();
        aula.setIdAula(vo.getId()); // solo si estás actualizando
        aula.setNombre(vo.getNombre());
        aula.setCapacidad(vo.getCapacidad());
        aula.setEstado(vo.getEstado());
        return aula;
    }
}
