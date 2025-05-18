package com.biblioweb.backend.repository;

import com.biblioweb.backend.entity.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    // Buscar reseñas por ID del libro
	List<Resena> findByLibro_IdLibro(Long idLibro);
	
	// Buscar todas las reseñas ordenadas por fecha descendente
	List<Resena> findAllByOrderByFechaDesc();


}