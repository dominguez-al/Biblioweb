package com.biblioweb.backend.service;

import com.biblioweb.backend.entity.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para la lógica de negocio de usuarios.
 * Define las operaciones disponibles en el servicio.
 */
public interface UsuarioService {

    /**
     * Lista todos los usuarios existentes.
     * @return lista de usuarios
     */
    List<Usuario> listarUsuarios();

    /**
     * Obtiene un usuario por su ID.
     * @param id ID del usuario
     * @return usuario encontrado (opcional)
     */
    Optional<Usuario> obtenerUsuarioPorId(Long id);

    /**
     * Guarda un nuevo usuario.
     * @param usuario entidad usuario con los datos de registro
     * @return usuario guardado
     */
    Usuario guardarUsuario(Usuario usuario);

    /**
     * Actualiza un usuario existente.
     * @param id ID del usuario a actualizar
     * @param usuario entidad con datos actualizados
     * @return usuario actualizado
     */
    Usuario actualizarUsuario(Long id, Usuario usuario);

    /**
     * Elimina un usuario por ID.
     * @param id ID del usuario a eliminar
     */
    void eliminarUsuario(Long id);
}
