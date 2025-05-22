package com.biblioweb.backend.service;

import com.biblioweb.backend.entity.Libro;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define la lógica de negocio relacionada con la gestión de libros.
 * Permite desacoplar el controlador de la lógica interna del sistema.
 */
public interface LibroService {

    /**
     * Devuelve todos los libros registrados en el sistema.
     *
     * @return lista completa de libros
     */
    List<Libro> listarLibros();

    /**
     * Busca un libro por su ID.
     *
     * @param idLibro ID del libro
     * @return Optional que puede contener el libro si se encuentra
     */
    Optional<Libro> obtenerLibroPorId(Long idLibro);

    /**
     * Devuelve una lista con los últimos libros agregados al sistema.
     * Se suele usar en la pantalla principal o sección de novedades.
     *
     * @return lista de libros más recientes
     */
    List<Libro> obtenerUltimosLibros();

    /**
     * Devuelve una lista de libros más reservados (populares).
     * Útil para mostrar rankings de uso.
     *
     * @return lista de libros con más reservas
     */
    List<Libro> obtenerLibrosMasPopulares();

    /**
     * Guarda un nuevo libro en la base de datos.
     *
     * @param libro objeto libro a guardar
     * @return libro persistido con ID y fecha asignada
     */
    Libro guardarLibro(Libro libro);

    /**
     * Actualiza los datos de un libro existente.
     *
     * @param libro objeto con datos modificados
     * @return libro ya actualizado
     */
    Libro actualizarLibro(Libro libro);

    /**
     * Elimina un libro del sistema por su ID.
     *
     * @param idLibro identificador del libro a borrar
     */
    void eliminarLibro(Long idLibro);
}
