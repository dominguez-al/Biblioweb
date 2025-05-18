package com.biblioweb.backend.repository;

import com.biblioweb.backend.entity.UsuarioAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UsuarioAulaRepository extends JpaRepository<UsuarioAula, Long> {

    // Verifica si un usuario ya tiene una reserva en una fecha específica
	boolean existsByUsuario_IdUsuarioAndAula_IdAulaAndFechaReservaAula(Long idUsuario, Long idAula, LocalDate fechaReservaAula);
	
	boolean existsByAula_IdAulaAndFechaReservaAula(Long idAula, LocalDate fechaReservaAula);

	List<UsuarioAula> findByUsuario_IdUsuario(Long idUsuario);
	
	@Query("SELECT ua.fechaReservaAula FROM UsuarioAula ua WHERE ua.aula.idAula = :idAula")
	
	List<LocalDate> findFechasReservadasPorAula(@Param("idAula") Long idAula);



}
