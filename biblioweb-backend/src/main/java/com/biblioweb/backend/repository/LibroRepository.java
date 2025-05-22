package com.biblioweb.backend.repository;

import com.biblioweb.backend.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de acceso a datos para la entidad Libro.
 * Extiende JpaRepository, lo que proporciona acceso completo a operaciones CRUD y paginación.
 */
@Repository // Marca esta interfaz como un componente de acceso a datos
public interface LibroRepository extends JpaRepository<Libro, Long> {

    /**
     * Devuelve los 2 libros más recientes (ordenados por fecha de alta descendente).
     * Equivalente a: SELECT * FROM libro ORDER BY fecha_alta_libro DESC LIMIT 2;
     */
    List<Libro> findTop2ByOrderByFechaAltaLibroDesc();

    /**
     * Devuelve los 2 libros más populares (ordenados por cantidad de reservas descendente).
     * Equivalente a: SELECT * FROM libro ORDER BY total_reservas DESC LIMIT 2;
     */
    List<Libro> findTop2ByOrderByTotalReservasDesc();
}
