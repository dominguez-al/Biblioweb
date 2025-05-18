package com.biblioweb.backend.controller;

import com.biblioweb.backend.entity.Libro;
import com.biblioweb.backend.mapper.LibroMapper;
import com.biblioweb.backend.service.LibroService;
import com.biblioweb.backend.vo.LibroPopularVO;
import com.biblioweb.backend.vo.LibroVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:4200") // o "*" para cualquier origen en desarrollo
@RestController // Indica que esta clase expone endpoints REST
@RequestMapping("/libros") // Prefijo de la ruta para todas las operaciones con libros
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/listar") // GET /libros/listar
    public List<LibroVO> listarLibros() {
        // Obtiene todos los libros y los transforma a VO (Vista)
        return libroService.listarLibros()
                .stream()
                .map(LibroMapper::toVO)
                .collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}") // GET /libros/ver/{id}
    public LibroVO obtenerLibro(@PathVariable Long id) {
        // Busca un libro por su ID y lo transforma en VO
        Optional<Libro> libroOpt = libroService.obtenerLibroPorId(id);
        return libroOpt.map(LibroMapper::toVO)
                       .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }
    
    @GetMapping("/ultimos")
    public List<LibroVO> obtenerUltimosLibros() {
        return libroService.obtenerUltimosLibros()  // Este método lo crearemos en el servicio
                .stream()
                .map(LibroMapper::toVO)
                .collect(Collectors.toList());
    }


    @PostMapping("/crear") // POST /libros/crear
    public LibroVO crearLibro(@RequestBody LibroVO vo) {
        // Convierte el VO en entidad, lo guarda, y retorna como VO
        Libro libro = LibroMapper.toEntity(vo);
        Libro guardado = libroService.guardarLibro(libro);
        return LibroMapper.toVO(guardado);
    }

    @PutMapping("/actualizar/{id}") // PUT /libros/actualizar/{id}
    public LibroVO actualizarLibro(@PathVariable Long id, @RequestBody LibroVO vo) {
        // Convierte el VO en entidad, asegura el ID correcto, actualiza
        Libro libro = LibroMapper.toEntity(vo);
        libro.setIdLibro(id);
        Libro actualizado = libroService.actualizarLibro(libro);
        return LibroMapper.toVO(actualizado);
    }

    @DeleteMapping("/borrar/{id}") // DELETE /libros/borrar/{id}
    public void eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
    }
    
    @GetMapping("/populares")
    public List<LibroVO> getLibrosPopulares() {
        return libroService.obtenerLibrosMasPopulares().stream()
                .map(LibroMapper::toVO)
                .collect(Collectors.toList());
    }


    
}

