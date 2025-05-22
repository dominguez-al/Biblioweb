package com.biblioweb.backend.serviceimpl;

import com.biblioweb.backend.entity.Resena;
import com.biblioweb.backend.repository.ResenaRepository;
import com.biblioweb.backend.service.ResenaService;
import com.biblioweb.backend.vo.ResenaVO;
import com.biblioweb.backend.mapper.ResenaMapper;
import com.biblioweb.backend.entity.Libro;
import com.biblioweb.backend.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio de reseñas.
 * Contiene la lógica de negocio para listar y guardar reseñas,
 * usando mapeadores y repositorios para acceder y transformar datos.
 */
@Service
public class ResenaServiceImpl implements ResenaService {

    @Autowired
    private ResenaRepository resenaRepository; // Acceso a la tabla de reseñas

    @Autowired
    private LibroRepository libroRepository; // Necesario para vincular una reseña con un libro

    /**
     * Obtiene todas las reseñas asociadas a un libro específico.
     * 
     * @param libroId ID del libro
     * @return lista de ResenaVO correspondientes al libro
     */
    @Override
    public List<ResenaVO> obtenerPorLibro(Long libroId) {
        return resenaRepository.findByLibro_IdLibro(libroId)
                .stream()
                .map(ResenaMapper::toVO) // Convertimos cada entidad a VO
                .toList();
    }

    /**
     * Devuelve todas las reseñas del sistema, ordenadas de más reciente a más antigua.
     *
     * @return lista de ResenaVO ordenadas por fecha
     */
    @Override
    public List<ResenaVO> obtenerTodas() {
        return resenaRepository.findAllByOrderByFechaDesc().stream()
                .map(ResenaMapper::toVO)
                .toList();
    }

    /**
     * Guarda una nueva reseña vinculada a un libro existente.
     * 
     * @param vo objeto de vista con los datos de la reseña
     * @return reseña guardada y convertida a VO
     */
    @Override
    public ResenaVO guardar(ResenaVO vo) {
        // Convertimos el VO a entidad
        Resena resena = ResenaMapper.toEntity(vo);

        // Buscamos el libro al que pertenece la reseña
        Libro libro = libroRepository.findById(vo.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Asignamos el libro y la fecha actual a la reseña
        resena.setLibro(libro);
        resena.setFecha(LocalDateTime.now());

        // Guardamos en la base de datos
        Resena guardada = resenaRepository.save(resena);

        // Devolvemos el VO de la reseña guardada
        return ResenaMapper.toVO(guardada);
    }
}

