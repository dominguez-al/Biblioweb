package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Aula;
import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.entity.UsuarioAula;
import com.biblioweb.backend.vo.UsuarioAulaVO;

/**
 * Mapper para convertir entre UsuarioAula y UsuarioAulaVO
 */

public class UsuarioAulaMapper {

    public static UsuarioAulaVO toVO(UsuarioAula entity) {
        UsuarioAulaVO vo = new UsuarioAulaVO();
        vo.setId(entity.getIdReservaAula());
        vo.setIdUsuario(entity.getUsuario().getIdUsuario());
        vo.setIdAula(entity.getAula().getIdAula());
        vo.setFechaReservaAula(entity.getFechaReservaAula());
        vo.setFechaDevolucionAula(entity.getFechaDevolucionAula());
        vo.setNombreAula(entity.getAula().getNombre());
        vo.setReservadoPor(entity.getUsuario().getEmail());
        return vo;
    }

    public static UsuarioAula toEntity(UsuarioAulaVO vo, Usuario usuario, Aula aula) {
        UsuarioAula entity = new UsuarioAula();
        entity.setIdReservaAula(vo.getId());
        entity.setUsuario(usuario);
        entity.setAula(aula);
        entity.setFechaReservaAula(vo.getFechaReservaAula());
        entity.setFechaDevolucionAula(vo.getFechaDevolucionAula());
        return entity;
    }
}

