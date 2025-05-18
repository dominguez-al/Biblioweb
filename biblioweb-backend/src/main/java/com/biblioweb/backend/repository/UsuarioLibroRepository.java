package com.biblioweb.backend.repository;

import com.biblioweb.backend.entity.UsuarioLibro;
import com.biblioweb.backend.vo.LibroPopularVO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioLibroRepository extends JpaRepository<UsuarioLibro, Long> {

    @Query("SELECT ul FROM UsuarioLibro ul JOIN FETCH ul.libro WHERE ul.usuario.idUsuario = :idUsuario")
    List<UsuarioLibro> findByUsuarioIdConLibro(@Param("idUsuario") Long idUsuario);


}
