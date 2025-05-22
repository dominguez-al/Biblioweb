package com.biblioweb.backend.repository;

import com.biblioweb.backend.entity.UsuarioAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para gestionar reservas de aulas (UsuarioAula).
 * Extiende JpaRepository para operaciones CRUD y define consultas personalizadas.
 */
@Repository
public interface UsuarioAulaRepository extends JpaRepository<UsuarioAula, Long> {

    /**
     * Verifica si un usuario ya tiene una reserva para un aula en una fecha específica.
     * Útil para evitar duplicidades al momento de crear nuevas reservas.
     *
     * @param idUsuario ID del usuario
     * @param idAula ID del aula
     * @param fechaReservaAula Fecha de la reserva
     * @return true si existe una reserva que coincide con esos datos
     */
    boolean existsByUsuario_IdUsuarioAndAula_IdAulaAndFechaReservaAula(Long idUsuario, Long idAula, LocalDate fechaReservaAula);

    /**
     * Verifica si un aula está ocupada en una fecha específica, sin importar el usuario.
     *
     * @param idAula ID del aula
     * @param fechaReservaAula Fecha de la reserva
     * @return true si el aula ya está reservada ese día
     */
    boolean existsByAula_IdAulaAndFechaReservaAula(Long idAula, LocalDate fechaReservaAula);

    /**
     * Devuelve todas las reservas hechas por un usuario.
     *
     * @param idUsuario ID del usuario
     * @return lista de reservas hechas por el usuario
     */
    List<UsuarioAula> findByUsuario_IdUsuario(Long idUsuario);

    /**
     * Consulta personalizada que devuelve todas las fechas reservadas de un aula específica.
     * Útil para mostrar en el calendario del frontend qué días no están disponibles.
     *
     * @param idAula ID del aula
     * @return lista de fechas reservadas
     */
    @Query("SELECT ua.fechaReservaAula FROM UsuarioAula ua WHERE ua.aula.idAula = :idAula")
    List<LocalDate> findFechasReservadasPorAula(@Param("idAula") Long idAula);
}
