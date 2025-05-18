package com.biblioweb.backend.service;

import com.biblioweb.backend.entity.Aula;
import com.biblioweb.backend.vo.AulaVO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para la lógica de negocio relacionada con aulas.
 */
public interface AulaService {

    /**
     * Devuelve la lista de todas las aulas registradas.
     */
    List<Aula> listarAulas();

    /**
     * Busca un aula por su ID.
     * @param id ID del aula
     * @return Optional con el aula encontrada (o vacío si no existe)
     */
    Optional<Aula> obtenerAulaPorId(Long id);

    /**
     * Guarda una nueva aula.
     * @param aula objeto aula a guardar
     * @return aula guardada
     */
    Aula guardarAula(Aula aula);

    /**
     * Actualiza un aula existente.
     * @param aula entidad con los datos actualizados
     * @return aula actualizada
     */
    Aula actualizarAula(Aula aula);

    /**
     * Elimina un aula por su ID.
     * @param id identificador del aula
     */
    void eliminarAula(Long id);
    
    
    List<AulaVO> listarAulasConEstadoHoy();
}
