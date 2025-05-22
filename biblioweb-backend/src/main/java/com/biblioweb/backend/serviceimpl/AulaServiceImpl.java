package com.biblioweb.backend.serviceimpl;

import com.biblioweb.backend.entity.Aula;
import com.biblioweb.backend.repository.AulaRepository;
import com.biblioweb.backend.repository.UsuarioAulaRepository;
import com.biblioweb.backend.service.AulaService;
import com.biblioweb.backend.vo.AulaVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación concreta del servicio de aulas.
 * Contiene la lógica de negocio y de acceso a datos relacionada con las aulas.
 */
@Service
public class AulaServiceImpl implements AulaService {

    @Autowired
    private AulaRepository aulaRepository; // Repositorio de acceso a la tabla 'aula'
    
    @Autowired
    private UsuarioAulaRepository usuarioAulaRepository; // Repositorio para verificar reservas

    /**
     * Devuelve la lista de todas las aulas.
     */
    @Override
    public List<Aula> listarAulas() {
        return aulaRepository.findAll();
    }

    /**
     * Busca un aula por su ID.
     */
    @Override
    public Optional<Aula> obtenerAulaPorId(Long id) {
        return aulaRepository.findById(id);
    }

    /**
     * Guarda una nueva aula en la base de datos.
     */
    @Override
    public Aula guardarAula(Aula aula) {
        return aulaRepository.save(aula);
    }

    /**
     * Actualiza los datos de una aula existente.
     */
    @Override
    public Aula actualizarAula(Aula aula) {
        return aulaRepository.save(aula);
    }

    /**
     * Elimina un aula por su ID.
     */
    @Override
    public void eliminarAula(Long id) {
        aulaRepository.deleteById(id);
    }

    /**
     * Devuelve la lista de aulas con su estado actual (ocupado o disponible) para el día de hoy.
     * Recorre todas las aulas y consulta si hay una reserva asociada para la fecha actual.
     *
     * @return Lista de AulaVO con estado contextual
     */
    @Override
    public List<AulaVO> listarAulasConEstadoHoy() {
        List<Aula> aulas = aulaRepository.findAll();        // Obtener todas las aulas
        LocalDate hoy = LocalDate.now();                    // Fecha actual

        return aulas.stream().map(aula -> {
            // Verificar si el aula está reservada hoy
            boolean ocupada = usuarioAulaRepository.existsByAula_IdAulaAndFechaReservaAula(
                aula.getIdAula(), hoy
            );

            // Crear VO con el estado "ocupado" o "disponible"
            return new AulaVO(
                aula.getIdAula(),
                aula.getNombre(),
                aula.getCapacidad(),
                ocupada ? "ocupado" : "disponible"
            );
        }).collect(Collectors.toList());
    }
}

