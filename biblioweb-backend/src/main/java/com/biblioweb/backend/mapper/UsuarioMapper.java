package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.vo.UsuarioCrearVO;
import com.biblioweb.backend.vo.UsuarioVO;

/**
 * Clase utilitaria para convertir entre la entidad Usuario y sus diferentes VO.
 */
public class UsuarioMapper {

    // Convierte una entidad Usuario a un VO para mostrar en la API
    public static UsuarioVO toVO(Usuario usuario) {
        UsuarioVO vo = new UsuarioVO();
        vo.setId(usuario.getIdUsuario());
        vo.setNombre(usuario.getNombre());
        vo.setEmail(usuario.getEmail());
        vo.setFAltaUsuario(usuario.getFAltaUsuario());
        return vo;
    }

    // Convierte un VO de creación a una entidad Usuario (sin ID ni fecha)
    public static Usuario fromCrearVO(UsuarioCrearVO crearVO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(crearVO.getNombre());
        usuario.setEmail(crearVO.getEmail());
        usuario.setPassword(crearVO.getPassword());
        // La fecha se asigna automáticamente por @PrePersist en la entidad
        return usuario;
    }
}
