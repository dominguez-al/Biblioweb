package com.biblioweb.backend.service;

import com.biblioweb.backend.vo.ResenaVO;
import java.util.List;

/**
 * Interfaz que define la lógica de negocio para la gestión de reseñas.
 * Permite separar el controlador de la lógica de persistencia y transformación.
 */
public interface ResenaService {

    /**
     * Devuelve todas las reseñas asociadas a un libro específico.
     *
     * @param libroId ID del libro
     * @return lista de reseñas del libro, transformadas como VO
     */
    List<ResenaVO> obtenerPorLibro(Long libroId);

    /**
     * Devuelve todas las reseñas del sistema, ordenadas (por ejemplo, por fecha).
     *
     * @return lista completa de reseñas, cada una como VO
     */
    List<ResenaVO> obtenerTodas();

    /**
     * Guarda una nueva reseña en la base de datos.
     *
     * @param vo Objeto de vista recibido desde el frontend
     * @return VO de la reseña recién guardada
     */
    ResenaVO guardar(ResenaVO vo);
}
