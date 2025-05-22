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

//Anotación que marca esta clase como un controlador REST de Spring
@RestController 

//Prefijo común para todas las rutas de este controlador: /aulas
@RequestMapping("/aulas") 
public class AulaController {

 // Inyección del servicio que maneja la lógica de negocio de aulas
 @Autowired
 private AulaService aulaService;

 // Endpoint para obtener la lista de aulas: GET /aulas/listar
 @GetMapping("/listar")
 public List<AulaVO> listarAulas() {
     // Llama al servicio para obtener todas las aulas,
     // las convierte a objetos VO (Vista/DTO) y devuelve la lista
     return aulaService.listarAulas()
             .stream()
             .map(AulaMapper::toVO) // Convierte cada entidad Aula a AulaVO
             .collect(Collectors.toList());
 }

 // Endpoint para obtener una aula por su ID: GET /aulas/ver/{id}
 @GetMapping("/ver/{id}")
 public AulaVO obtenerAula(@PathVariable Long id) {
     // Busca el aula en la base de datos. Si no existe, lanza una excepción.
     Optional<Aula> aulaOpt = aulaService.obtenerAulaPorId(id);
     return aulaOpt.map(AulaMapper::toVO)
                   .orElseThrow(() -> new RuntimeException("Aula no encontrada"));
 }

 // Endpoint para crear una nueva aula: POST /aulas/crear
 @PostMapping("/crear")
 public AulaVO crearAula(@RequestBody AulaVO vo) {
     // Convierte el VO recibido desde el cliente a una entidad,
     // guarda el aula en la base de datos y devuelve el resultado como VO
     Aula aula = AulaMapper.toEntity(vo);
     Aula guardada = aulaService.guardarAula(aula);
     return AulaMapper.toVO(guardada);
 }

 // Endpoint para actualizar una aula existente: PUT /aulas/actualizar/{id}
 @PutMapping("/actualizar/{id}")
 public AulaVO actualizarAula(@PathVariable Long id, @RequestBody AulaVO vo) {
     // Asigna el ID al objeto convertido y realiza la actualización
     Aula aula = AulaMapper.toEntity(vo);
     aula.setIdAula(id); // Se asegura de que el ID de la entidad coincida con el de la URL
     Aula actualizada = aulaService.actualizarAula(aula);
     return AulaMapper.toVO(actualizada);
 }

 // Endpoint para eliminar un aula por su ID: DELETE /aulas/borrar/{id}
 @DeleteMapping("/borrar/{id}")
 public void eliminarAula(@PathVariable Long id) {
     // Llama al servicio para eliminar el aula
     aulaService.eliminarAula(id);
 }
}
