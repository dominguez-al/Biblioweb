package com.biblioweb.backend.repository;

import com.biblioweb.backend.entity.UsuarioLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para gestionar reservas de libros (UsuarioLibro).
 * Extiende JpaRepository para operaciones CRUD estándar y define una consulta personalizada.
 */
public interface UsuarioLibroRepository extends JpaRepository<UsuarioLibro, Long> {

    /**
     * Consulta personalizada para obtener todas las reservas de un usuario
     * incluyendo los datos completos del libro (con JOIN FETCH para evitar lazy loading).
     *
     * @param idUsuario ID del usuario
     * @return lista de reservas con información del libro cargada
     */
    @Query("SELECT ul FROM UsuarioLibro ul JOIN FETCH ul.libro WHERE ul.usuario.idUsuario = :idUsuario")
    List<UsuarioLibro> findByUsuarioIdConLibro(@Param("idUsuario") Long idUsuario);
}
