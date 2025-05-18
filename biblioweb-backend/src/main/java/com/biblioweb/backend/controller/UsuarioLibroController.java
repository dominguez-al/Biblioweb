package com.biblioweb.backend.controller;

import com.biblioweb.backend.entity.UsuarioLibro;
import com.biblioweb.backend.mapper.UsuarioLibroMapper;
import com.biblioweb.backend.service.UsuarioLibroService;
import com.biblioweb.backend.vo.LibroPopularVO;
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

    @GetMapping("/listar") // Maneja GET /reservas-libros/listar
    public List<UsuarioLibroVO> listarReservas() {
        // Convierte la lista de entidades en VO para devolver solo lo necesario
        return usuarioLibroService.listarReservas()
                .stream()
                .map(UsuarioLibroMapper::toVO)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/mis-reservas")
    public List<UsuarioLibroVO> obtenerReservasDelUsuario(@RequestParam Long idUsuario) {
        List<UsuarioLibro> reservas = usuarioLibroService.obtenerReservasPorUsuario(idUsuario);
        return reservas.stream()
                       .map(UsuarioLibroMapper::toVO)
                       .collect(Collectors.toList());
    }



    @PostMapping("/crear") // Maneja POST /reservas-libros/crear
    public UsuarioLibroVO crearReserva(@RequestBody UsuarioLibroVO vo) {
        // Usa el servicio para crear la reserva desde un VO
        UsuarioLibro reserva = usuarioLibroService.crearDesdeVO(vo);
        return UsuarioLibroMapper.toVO(reserva);
    }

    @DeleteMapping("/borrar/{id}") // Maneja DELETE /reservas-libros/borrar/{id}
    public void eliminarReserva(@PathVariable Long id) {
        usuarioLibroService.eliminarReserva(id);
    }
    
}
