package com.biblioweb.backend.serviceimpl;

import com.biblioweb.backend.entity.Libro;
import com.biblioweb.backend.repository.LibroRepository;
import com.biblioweb.backend.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación concreta de la interfaz LibroService.
 * Contiene la lógica de negocio para gestionar libros, utilizando el repositorio para acceso a datos.
 */
@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository; // Acceso a la base de datos de libros

    /**
     * Lista todos los libros registrados.
     *
     * @return lista de libros
     */
    @Override
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    /**
     * Busca un libro por su ID.
     *
     * @param id ID del libro
     * @return Optional con el libro si existe
     */
    @Override
    public Optional<Libro> obtenerLibroPorId(Long id) {
        return libroRepository.findById(id);
    }

    /**
     * Guarda un nuevo libro (alta).
     *
     * @param libro entidad libro
     * @return libro persistido
     */
    @Override
    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    /**
     * Actualiza un libro existente.
     * Usa el mismo método `save()` ya que JPA detecta si es un update por el ID presente.
     *
     * @param libro libro con cambios
     * @return libro actualizado
     */
    @Override
    public Libro actualizarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    /**
     * Obtiene los últimos 2 libros agregados (ordenados por fecha de alta descendente).
     *
     * @return lista de libros recientes
     */
    @Override
    public List<Libro> obtenerUltimosLibros() {
        return libroRepository.findTop2ByOrderByFechaAltaLibroDesc();
    }

    /**
     * Obtiene los 2 libros más reservados (más populares).
     *
     * @return lista de libros con mayor número de reservas
     */
    @Override
    public List<Libro> obtenerLibrosMasPopulares() {
        return libroRepository.findTop2ByOrderByTotalReservasDesc();
    }

    /**
     * Elimina un libro por su ID.
     *
     * @param id ID del libro a eliminar
     */
    @Override
    public void eliminarLibro(Long id) {
        libroRepository.deleteById(id);
    }
}
