package com.biblioweb.backend.controller;

import com.biblioweb.backend.entity.Aula;
import com.biblioweb.backend.mapper.AulaMapper;
import com.biblioweb.backend.service.AulaService;
import com.biblioweb.backend.vo.AulaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController // Define esta clase como un controlador REST
@RequestMapping("/aulas") // Prefijo de URL para todas las rutas relacionadas con aulas
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @GetMapping("/listar") // GET /aulas/listar
    public List<AulaVO> listarAulas() {
        // Convierte todas las entidades Aula a sus respectivos VO y devuelve la lista
        return aulaService.listarAulas()
                .stream()
                .map(AulaMapper::toVO)
                .collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}") // GET /aulas/ver/{id}
    public AulaVO obtenerAula(@PathVariable Long id) {
        // Busca un aula por su ID, la transforma en VO y la devuelve
        Optional<Aula> aulaOpt = aulaService.obtenerAulaPorId(id);
        return aulaOpt.map(AulaMapper::toVO)
                      .orElseThrow(() -> new RuntimeException("Aula no encontrada"));
    }

    @PostMapping("/crear") // POST /aulas/crear
    public AulaVO crearAula(@RequestBody AulaVO vo) {
        // Convierte el VO recibido en una entidad Aula, lo guarda y devuelve el VO del aula creada
        Aula aula = AulaMapper.toEntity(vo);
        Aula guardada = aulaService.guardarAula(aula);
        return AulaMapper.toVO(guardada);
    }

    @PutMapping("/actualizar/{id}") // PUT /aulas/actualizar/{id}
    public AulaVO actualizarAula(@PathVariable Long id, @RequestBody AulaVO vo) {
        // Asegura que el ID es el correcto, convierte el VO a entidad y lo actualiza
        Aula aula = AulaMapper.toEntity(vo);
        aula.setIdAula(id);
        Aula actualizada = aulaService.actualizarAula(aula);
        return AulaMapper.toVO(actualizada);
    }

    @DeleteMapping("/borrar/{id}") // DELETE /aulas/borrar/{id}
    public void eliminarAula(@PathVariable Long id) {
        aulaService.eliminarAula(id);
    }
}

