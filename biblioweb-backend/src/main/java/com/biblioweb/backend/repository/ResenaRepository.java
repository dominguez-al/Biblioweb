package com.biblioweb.backend.repository;

import com.biblioweb.backend.entity.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para la entidad Resena.
 * Extiende JpaRepository para obtener acceso a operaciones CRUD básicas
 * y define métodos personalizados para consultas específicas sobre reseñas.
 */
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    /**
     * Busca todas las reseñas asociadas a un libro por su ID.
     * Equivale a:
     * SELECT * FROM resena WHERE libro_id = ?;
     *
     * @param idLibro ID del libro
     * @return lista de reseñas de ese libro
     */
    List<Resena> findByLibro_IdLibro(Long idLibro);

    /**
     * Devuelve todas las reseñas ordenadas por fecha de publicación en orden descendente.
     * Equivale a:
     * SELECT * FROM resena ORDER BY fecha DESC;
     *
     * @return lista de reseñas más recientes primero
     */
    List<Resena> findAllByOrderByFechaDesc();
}
