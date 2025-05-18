package com.biblioweb.backend.controller;

import com.biblioweb.backend.entity.Resena;
import com.biblioweb.backend.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.biblioweb.backend.vo.ResenaVO;


import java.util.List;

@RestController
@RequestMapping("/resenas")
@CrossOrigin(origins = "http://localhost:4200") // Ajusta según tu frontend
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    // GET: obtener todas las reseñas de un libro
    @GetMapping("/{libroId}")
    public List<ResenaVO> obtenerPorLibro(@PathVariable Long libroId) {
        return resenaService.obtenerPorLibro(libroId);
    }

    // POST: guardar una nueva reseña
    @PostMapping("/guardarResena")
    public ResenaVO guardar(@RequestBody ResenaVO vo) {
    	 System.out.println("🔥 Llegó reseña: " + vo.getComentario() + ", usuario: " + vo.getUsuarioId());
        return resenaService.guardar(vo);
    }
    
    // GET: obtener todas las reseñas
    @GetMapping("/todas")
    public List<ResenaVO> obtenerTodas() {
        return resenaService.obtenerTodas();
    }

}