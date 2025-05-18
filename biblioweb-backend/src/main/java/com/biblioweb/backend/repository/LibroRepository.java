package com.biblioweb.backend.repository;

import com.biblioweb.backend.entity.Libro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
	List<Libro> findTop2ByOrderByFechaAltaLibroDesc();
	List<Libro> findTop2ByOrderByTotalReservasDesc();
}
