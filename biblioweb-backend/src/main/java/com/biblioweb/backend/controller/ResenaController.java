package com.biblioweb.backend.controller;


import com.biblioweb.backend.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.biblioweb.backend.vo.ResenaVO;


import java.util.List;

@RestController // Declara que esta clase es un controlador REST
@RequestMapping("/resenas") // Prefijo para todas las rutas: /resenas/*
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde el frontend (Angular por ejemplo)
public class ResenaController {

    @Autowired
    private ResenaService resenaService; // Servicio que contiene la lógica para manejar reseñas

    /**
     * Endpoint para obtener todas las reseñas de un libro específico.
     * GET /resenas/{libroId}
     */
    @GetMapping("/{libroId}")
    public List<ResenaVO> obtenerPorLibro(@PathVariable Long libroId) {
        // Devuelve todas las reseñas asociadas a un libro específico
        return resenaService.obtenerPorLibro(libroId);
    }

    /**
     * Endpoint para guardar una nueva reseña.
     * POST /resenas/guardarResena
     */
    @PostMapping("/guardarResena")
    public ResenaVO guardar(@RequestBody ResenaVO vo) {
        // Muestra en consola información básica de la reseña recibida (útil para depuración)
        System.out.println("🔥 Llegó reseña: " + vo.getComentario() + ", usuario: " + vo.getUsuarioId());
        
        // Guarda la reseña y la devuelve
        return resenaService.guardar(vo);
    }
    
    /**
     * Endpoint para obtener todas las reseñas del sistema.
     * GET /resenas/todas
     */
    @GetMapping("/todas")
    public List<ResenaVO> obtenerTodas() {
        // Devuelve la lista completa de reseñas
        return resenaService.obtenerTodas();
    }
}
