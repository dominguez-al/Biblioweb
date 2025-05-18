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

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/usuarios") // Define la ruta base para todas las operaciones de usuario
public class UsuarioController {

    @Autowired // Inyección del servicio
    private UsuarioService usuarioService;

    @GetMapping("/listar") // GET /usuarios/listar
    public List<UsuarioVO> listarUsuarios() {
        // Llama al servicio, convierte la lista de entidades a VO (vista)
        return usuarioService.listarUsuarios()
                .stream()
                .map(UsuarioMapper::toVO)
                .collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}") // GET /usuarios/ver/{id}
    public UsuarioVO obtenerUsuario(@PathVariable Long id) {
        // Busca el usuario por ID, si no lo encuentra lanza excepción
        Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuarioPorId(id);
        return usuarioOpt.map(UsuarioMapper::toVO)
                         .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @PostMapping("/crear") // POST /usuarios/crear
    public UsuarioVO crearUsuario(@RequestBody UsuarioCrearVO crearVO) {
        // Convierte el VO de creación en entidad y guarda
        Usuario usuario = UsuarioMapper.fromCrearVO(crearVO);
        Usuario guardado = usuarioService.guardarUsuario(usuario);
        return UsuarioMapper.toVO(guardado);
    }

    @PutMapping("/actualizar/{id}") // PUT /usuarios/actualizar/{id}
    public UsuarioVO actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioCrearVO crearVO) {
        // Actualiza los datos del usuario usando el mapper y el servicio
        Usuario actualizado = usuarioService.actualizarUsuario(id, UsuarioMapper.fromCrearVO(crearVO));
        return UsuarioMapper.toVO(actualizado);
    }

    @DeleteMapping("/borrar/{id}") // DELETE /usuarios/borrar/{id}
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
}
