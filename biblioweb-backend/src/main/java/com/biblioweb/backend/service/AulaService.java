package com.biblioweb.backend.service;

import com.biblioweb.backend.entity.Aula;
import com.biblioweb.backend.vo.AulaVO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define la lógica de negocio relacionada con la gestión de aulas.
 * Implementaciones de esta interfaz manejarán la interacción entre el controlador y el repositorio.
 */
public interface AulaService {

    /**
     * Devuelve todas las aulas disponibles en el sistema.
     * Se usa, por ejemplo, para listar aulas en un panel de administración o selección.
     *
     * @return Lista completa de aulas (entidades)
     */
    List<Aula> listarAulas();

    /**
     * Busca un aula específica por su ID.
     *
     * @param id ID del aula
     * @return Optional que puede contener el aula si existe, o estar vacío si no se encuentra
     */
    Optional<Aula> obtenerAulaPorId(Long id);

    /**
     * Guarda una nueva aula en la base de datos.
     *
     * @param aula Objeto aula a guardar (sin ID aún)
     * @return Aula persistida (con ID asignado)
     */
    Aula guardarAula(Aula aula);

    /**
     * Actualiza los datos de un aula existente.
     *
     * @param aula Aula con los campos actualizados (incluye ID)
     * @return Aula ya actualizada
     */
    Aula actualizarAula(Aula aula);

    /**
     * Elimina una aula del sistema según su ID.
     *
     * @param id Identificador único del aula a eliminar
     */
    void eliminarAula(Long id);

    /**
     * Lista las aulas incluyendo su estado actual para el día (por ejemplo, disponibles/ocupadas).
     * Se usa para mostrar la disponibilidad en tiempo real.
     *
     * @return Lista de objetos de vista (AulaVO) con información contextual del día
     */
    List<AulaVO> listarAulasConEstadoHoy();
}
