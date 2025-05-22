package com.biblioweb.backend.controller;

import com.biblioweb.backend.entity.Libro;
import com.biblioweb.backend.mapper.LibroMapper;
import com.biblioweb.backend.service.LibroService;
import com.biblioweb.backend.vo.LibroVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes CORS desde el frontend Angular
@RestController // Declara que esta clase es un controlador REST
@RequestMapping("/libros") // Prefijo común para todas las rutas de este controlador
public class LibroController {

    @Autowired
    private LibroService libroService; // Inyección del servicio que maneja la lógica de negocio de libros

    /**
     * Endpoint para listar todos los libros.
     * GET /libros/listar
     */
    @GetMapping("/listar")
    public List<LibroVO> listarLibros() {
        // Convierte la lista de entidades Libro a VO y la retorna
        return libroService.listarLibros()
                .stream()
                .map(LibroMapper::toVO)
                .collect(Collectors.toList());
    }

    /**
     * Endpoint para obtener un libro por su ID.
     * GET /libros/ver/{id}
     */
    @GetMapping("/ver/{id}")
    public LibroVO obtenerLibro(@PathVariable Long id) {
        // Busca el libro y lo convierte en VO si existe, si no lanza error
        Optional<Libro> libroOpt = libroService.obtenerLibroPorId(id);
        return libroOpt.map(LibroMapper::toVO)
                       .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    /**
     * Endpoint para obtener los últimos libros agregados.
     * GET /libros/ultimos
     */
    @GetMapping("/ultimos")
    public List<LibroVO> obtenerUltimosLibros() {
        // Llama al servicio para obtener los últimos libros
        return libroService.obtenerUltimosLibros()
                .stream()
                .map(LibroMapper::toVO)
                .collect(Collectors.toList());
    }

    /**
     * Endpoint para crear un nuevo libro.
     * POST /libros/crear
     */
    @PostMapping("/crear")
    public LibroVO crearLibro(@RequestBody LibroVO vo) {
        // Convierte el VO a entidad, guarda el libro y lo devuelve como VO
        Libro libro = LibroMapper.toEntity(vo);
        Libro guardado = libroService.guardarLibro(libro);
        return LibroMapper.toVO(guardado);
    }

    /**
     * Endpoint para actualizar un libro existente.
     * PUT /libros/actualizar/{id}
     */
    @PutMapping("/actualizar/{id}")
    public LibroVO actualizarLibro(@PathVariable Long id, @RequestBody LibroVO vo) {
        // Convierte el VO a entidad, asigna el ID, actualiza y devuelve el libro actualizado como VO
        Libro libro = LibroMapper.toEntity(vo);
        libro.setIdLibro(id);
        Libro actualizado = libroService.actualizarLibro(libro);
        return LibroMapper.toVO(actualizado);
    }

    /**
     * Endpoint para eliminar un libro por ID.
     * DELETE /libros/borrar/{id}
     */
    @DeleteMapping("/borrar/{id}")
    public void eliminarLibro(@PathVariable Long id) {
        // Elimina el libro usando el servicio
        libroService.eliminarLibro(id);
    }

    /**
     * Endpoint para obtener los libros más populares (por número de reservas, por ejemplo).
     * GET /libros/populares
     */
    @GetMapping("/populares")
    public List<LibroVO> getLibrosPopulares() {
        // Llama al servicio, transforma a VO y retorna la lista
        return libroService.obtenerLibrosMasPopulares().stream()
                .map(LibroMapper::toVO)
                .collect(Collectors.toList());
    }
}