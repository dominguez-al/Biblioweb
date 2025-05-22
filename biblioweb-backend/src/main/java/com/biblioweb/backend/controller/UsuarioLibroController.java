package com.biblioweb.backend.controller;

import com.biblioweb.backend.entity.UsuarioLibro;
import com.biblioweb.backend.mapper.UsuarioLibroMapper;
import com.biblioweb.backend.service.UsuarioLibroService;
import com.biblioweb.backend.vo.UsuarioLibroVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController // Marca esta clase como un controlador REST (maneja peticiones HTTP)
@RequestMapping("/reservas-libros") // Ruta base de todas las peticiones del controlador
public class UsuarioLibroController {

    @Autowired // Inyección del servicio que maneja la lógica de negocio
    private UsuarioLibroService usuarioLibroService;

    /**
     * GET /reservas-libros/listar
     * Devuelve la lista completa de reservas de libros.
     */
    @GetMapping("/listar")
    public List<UsuarioLibroVO> listarReservas() {
        // Llama al servicio para obtener las reservas y las transforma a VO
        return usuarioLibroService.listarReservas()
                .stream()
                .map(UsuarioLibroMapper::toVO) // Transforma cada reserva a su representación VO
                .collect(Collectors.toList());
    }

    /**
     * GET /reservas-libros/mis-reservas?idUsuario=...
     * Devuelve las reservas de un usuario específico.
     */
    @GetMapping("/mis-reservas")
    public List<UsuarioLibroVO> obtenerReservasDelUsuario(@RequestParam Long idUsuario) {
        // Obtiene las reservas del usuario y las convierte a VO
        List<UsuarioLibro> reservas = usuarioLibroService.obtenerReservasPorUsuario(idUsuario);
        return reservas.stream()
                       .map(UsuarioLibroMapper::toVO)
                       .collect(Collectors.toList());
    }

    /**
     * POST /reservas-libros/crear
     * Crea una nueva reserva de libro.
     */
    @PostMapping("/crear")
    public UsuarioLibroVO crearReserva(@RequestBody UsuarioLibroVO vo) {
        // Convierte el VO recibido en una entidad, guarda y devuelve como VO
        UsuarioLibro reserva = usuarioLibroService.crearDesdeVO(vo);
        return UsuarioLibroMapper.toVO(reserva);
    }

    /**
     * DELETE /reservas-libros/borrar/{id}
     * Elimina una reserva de libro por su ID.
     */
    @DeleteMapping("/borrar/{id}")
    public void eliminarReserva(@PathVariable Long id) {
        usuarioLibroService.eliminarReserva(id); // Llama al servicio para eliminar la reserva
    }
}
