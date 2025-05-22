package com.biblioweb.backend.serviceimpl;

import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.repository.UsuarioRepository;
import com.biblioweb.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de Usuario.
 * Gestiona operaciones como registro, edición, consulta y eliminación de usuarios.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Acceso a la tabla de usuarios

    @Autowired
    private PasswordEncoder passwordEncoder; // Codificación segura de contraseñas

    /**
     * Devuelve todos los usuarios almacenados.
     */
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario
     * @return Optional con el usuario si existe
     */
    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Guarda un nuevo usuario.
     * - Codifica la contraseña con BCrypt
     * - Asigna rol "USER" si no se especifica
     */
    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        // Validación: la contraseña no puede estar vacía
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

        // Encriptar contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar rol por defecto si no se especifica
        if (usuario.getRol() == null || usuario.getRol().isBlank()) {
            usuario.setRol("USER");
        }


        // Guardar usuario en BD
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza los campos editables de un usuario existente.
     * - No cambia contraseña por seguridad.
     * @param id ID del usuario a actualizar
     * @param usuarioActualizado objeto con nuevos valores
     * @return Usuario actualizado
     */
    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario existente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Campos actualizables (sin afectar contraseña)
        existente.setNombre(usuarioActualizado.getNombre());
        existente.setEmail(usuarioActualizado.getEmail());
        existente.setRol(usuarioActualizado.getRol());

        return usuarioRepository.save(existente);
    }

    /**
     * Elimina un usuario por su ID.
     * @param id identificador del usuario
     */
    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}

