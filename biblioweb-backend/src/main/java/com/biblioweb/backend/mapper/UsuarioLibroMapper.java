package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Libro;
import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.entity.UsuarioLibro;
import com.biblioweb.backend.vo.UsuarioLibroVO;

/**
 * Mapper para convertir entre UsuarioLibro y UsuarioLibroVO
 */
public class UsuarioLibroMapper {

    public static UsuarioLibroVO toVO(UsuarioLibro entity) {
        UsuarioLibroVO vo = new UsuarioLibroVO();
        vo.setId(entity.getIdReservaLibro());
        vo.setIdUsuario(entity.getUsuario().getIdUsuario());
        vo.setIdLibro(entity.getLibro().getIdLibro());
        vo.setFechaReservaLibro(entity.getFechaReservaLibro());
        vo.setFechaDevolucion(entity.getFechaDevolucion());
        vo.setImagen(entity.getLibro().getImagen()); // ← ¡importante!
        vo.setTitulo(entity.getLibro().getTitulo());


        return vo;
    }

    public static UsuarioLibro toEntity(UsuarioLibroVO vo, Usuario usuario, Libro libro) {
        UsuarioLibro entity = new UsuarioLibro();
        entity.setIdReservaLibro(vo.getId());
        entity.setUsuario(usuario);
        entity.setLibro(libro);
        entity.setFechaReservaLibro(vo.getFechaReservaLibro());
        entity.setFechaDevolucion(vo.getFechaDevolucion());
        return entity;
    }
}
