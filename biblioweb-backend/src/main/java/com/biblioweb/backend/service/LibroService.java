package com.biblioweb.backend.service;

import com.biblioweb.backend.entity.Libro;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los métodos del servicio de libros.
 * Esto permite desacoplar la lógica del controlador y facilitar testing y mantenimiento.
 */
public interface LibroService {
    

    List<Libro> listarLibros();

    Optional<Libro> obtenerLibroPorId(Long idLibro);
    
    
    List<Libro> obtenerUltimosLibros();
    

    List<Libro> obtenerLibrosMasPopulares();


    /**
     * Guarda un nuevo libro.
     * @param libro entidad a guardar
     * @return libro guardado
     */
    Libro guardarLibro(Libro libro);

    /**
     * Actualiza un libro existente.
     * @param libro entidad con los datos actualizados
     * @return libro actualizado
     */
    Libro actualizarLibro(Libro libro);

    /**
     * Elimina un libro por su ID.
     * @param id identificador del libro
     */
    void eliminarLibro(Long idLibro);
}

