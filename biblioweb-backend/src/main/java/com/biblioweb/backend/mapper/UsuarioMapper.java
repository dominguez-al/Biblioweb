package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.vo.UsuarioCrearVO;
import com.biblioweb.backend.vo.UsuarioVO;

/**
 * Clase utilitaria que se encarga de convertir entre:
 * - La entidad Usuario (modelo de base de datos)
 * - UsuarioVO (datos que se envían al cliente)
 * - UsuarioCrearVO (datos que recibe el backend al registrar un usuario)
 */
public class UsuarioMapper {

    /**
     * Convierte una entidad Usuario en un UsuarioVO.
     * Este método se usa para enviar datos al cliente sin exponer campos sensibles como la contraseña.
     *
     * @param usuario La entidad que proviene de la base de datos
     * @return Objeto de vista con los campos visibles para el frontend
     */
    public static UsuarioVO toVO(Usuario usuario) {
        UsuarioVO vo = new UsuarioVO();
        vo.setId(usuario.getIdUsuario());              // ID del usuario
        vo.setNombre(usuario.getNombre());             // Nombre
        vo.setEmail(usuario.getEmail());               // Email
        vo.setFAltaUsuario(usuario.getFAltaUsuario());// Fecha de alta (registro)
        vo.setRol(usuario.getRol());
        return vo;
    }

    /**
     * Convierte un UsuarioCrearVO (enviado desde el frontend al registrarse) en una entidad Usuario.
     * Nota: no se asigna el ID ni la fecha de alta aquí.
     * La fecha se asigna automáticamente con @PrePersist en la entidad.
     *
     * @param crearVO Objeto con los datos que el usuario ingresó al registrarse
     * @return Entidad Usuario lista para ser persistida en la base de datos
     */
    public static Usuario fromCrearVO(UsuarioCrearVO crearVO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(crearVO.getNombre());     // Nombre del nuevo usuario
        usuario.setEmail(crearVO.getEmail());       // Email
        usuario.setPassword(crearVO.getPassword());// Contraseña (normalmente se encripta en el servicio)
        usuario.setRol(crearVO.getRol());
        return usuario;
    }
}
