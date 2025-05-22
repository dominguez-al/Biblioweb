package com.biblioweb.backend.controller;

import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.mapper.UsuarioMapper;
import com.biblioweb.backend.service.UsuarioService;
import com.biblioweb.backend.vo.UsuarioCrearVO;
import com.biblioweb.backend.vo.UsuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController // Indica que esta clase expone endpoints REST
@RequestMapping("/usuarios") // Define el prefijo común para todas las rutas: /usuarios/*
public class UsuarioController {

    @Autowired // Inyección automática del servicio de usuarios
    private UsuarioService usuarioService;

    /**
     * GET /usuarios/listar
     * Devuelve una lista de todos los usuarios en formato VO (Vista/DTO).
     */
    @GetMapping("/listar")
    public List<UsuarioVO> listarUsuarios() {
        // Llama al servicio para obtener todos los usuarios, los convierte a VO y devuelve la lista
        return usuarioService.listarUsuarios()
                .stream()
                .map(UsuarioMapper::toVO)
                .collect(Collectors.toList());
    }

    /**
     * GET /usuarios/ver/{id}
     * Devuelve un usuario específico por su ID.
     */
    @GetMapping("/ver/{id}")
    public UsuarioVO obtenerUsuario(@PathVariable Long id) {
        // Busca el usuario por ID, y si no lo encuentra lanza excepción
        Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuarioPorId(id);
        return usuarioOpt.map(UsuarioMapper::toVO)
                         .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    /**
     * POST /usuarios/crear
     * Crea un nuevo usuario a partir de un VO de creación (UsuarioCrearVO).
     */
    @PostMapping("/crear")
    public UsuarioVO crearUsuario(@RequestBody UsuarioCrearVO crearVO) {
        // Convierte el VO de creación a entidad, guarda el usuario y devuelve su representación VO
        Usuario usuario = UsuarioMapper.fromCrearVO(crearVO);
        Usuario guardado = usuarioService.guardarUsuario(usuario);
        return UsuarioMapper.toVO(guardado);
    }

    /**
     * PUT /usuarios/actualizar/{id}
     * Actualiza un usuario existente con la información recibida.
     */
    @PutMapping("/actualizar/{id}")
    public UsuarioVO actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioCrearVO crearVO) {
        // Convierte el VO a entidad y realiza la actualización a través del servicio
        Usuario actualizado = usuarioService.actualizarUsuario(id, UsuarioMapper.fromCrearVO(crearVO));
        return UsuarioMapper.toVO(actualizado);
    }

    /**
     * DELETE /usuarios/borrar/{id}
     * Elimina un usuario por su ID.
     */
    @DeleteMapping("/borrar/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        // Llama al servicio para eliminar el usuario
        usuarioService.eliminarUsuario(id);
    }
}
