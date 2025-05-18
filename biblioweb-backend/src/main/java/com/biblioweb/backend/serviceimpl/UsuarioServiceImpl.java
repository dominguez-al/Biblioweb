package com.biblioweb.backend.serviceimpl;

import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.repository.UsuarioRepository;
import com.biblioweb.backend.service.CorreoService;
import com.biblioweb.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de UsuarioService.
 * Maneja la lógica de negocio para la entidad Usuario.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CorreoService correoService; // Servicio de envío de correos

    /**
     * Devuelve todos los usuarios registrados.
     */
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     */
    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Guarda un nuevo usuario.
     * - Codifica la contraseña
     * - Asigna rol por defecto
     * - Envía un correo de bienvenida
     */
    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        // Verificamos que la contraseña no sea nula o vacía
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

        // Codificamos la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignamos rol si no está definido
        if (usuario.getRol() == null) {
            usuario.setRol("USER");
        }

        Usuario guardado = usuarioRepository.save(usuario);

        // Enviamos correo de bienvenida
       /* correoService.enviarCorreo(
            guardado.getEmail(),
            "Bienvenido a Biblioweb",
            "¡Hola " + guardado.getNombre() + "!\n\nTu cuenta ha sido creada con éxito. 🎉"
        );*/

        return guardado;
    }


    /**
     * Actualiza un usuario existente usando su ID.
     */
    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Solo actualizas los campos editables
        existente.setNombre(usuarioActualizado.getNombre());
        existente.setEmail(usuarioActualizado.getEmail());

        // ⚠️ No cambies la contraseña aquí a menos que explícitamente se quiera
        return usuarioRepository.save(existente);
    }

    
    

    /**
     * Elimina un usuario por su ID.
     */
    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
