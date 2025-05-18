package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Resena;
import com.biblioweb.backend.vo.ResenaVO;

public class ResenaMapper {

    public static ResenaVO toVO(Resena resena) {
        ResenaVO vo = new ResenaVO();
        vo.setId(resena.getId());
        vo.setComentario(resena.getComentario());
        vo.setPuntuacion(resena.getPuntuacion());
        vo.setFecha(resena.getFecha());
        vo.setUsuarioId(resena.getUsuarioId());
        vo.setLibroId(resena.getLibro().getIdLibro());
        vo.setTituloLibro(resena.getLibro().getTitulo());
        return vo;
    }

    public static Resena toEntity(ResenaVO vo) {
        Resena resena = new Resena();
        resena.setId(vo.getId());
        resena.setComentario(vo.getComentario());
        resena.setPuntuacion(vo.getPuntuacion());
        resena.setFecha(vo.getFecha());
        resena.setUsuarioId(vo.getUsuarioId());
        return resena;
    }
}

