package com.biblioweb.backend.serviceimpl;

import com.biblioweb.backend.entity.Libro;
import com.biblioweb.backend.repository.LibroRepository;
import com.biblioweb.backend.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz LibroService.
 * Contiene la lógica de negocio relacionada con libros.
 */
@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository; // Inyección del repositorio para acceso a datos

    /**
     * Devuelve la lista de todos los libros.
     */
    @Override
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    /**
     * Devuelve un libro por su ID si existe.
     */
    @Override
    public Optional<Libro> obtenerLibroPorId(Long id) {
        return libroRepository.findById(id);
    }

    /**
     * Guarda un nuevo libro.
     */
    @Override
    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    /**
     * Actualiza un libro existente (por ID incluido en la entidad).
     */
    @Override
    public Libro actualizarLibro(Libro libro) {
        return libroRepository.save(libro); // save() sirve tanto para crear como para actualizar
    }


    @Override
    public List<Libro> obtenerUltimosLibros() {
        return libroRepository.findTop2ByOrderByFechaAltaLibroDesc();
    }
    
    @Override
    public List<Libro> obtenerLibrosMasPopulares() {
        return libroRepository.findTop2ByOrderByTotalReservasDesc();
    }

    

    /**
     * Elimina un libro por su ID.
     */
    @Override
    public void eliminarLibro(Long id) {
        libroRepository.deleteById(id);
    }
}
