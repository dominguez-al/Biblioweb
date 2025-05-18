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
 * Contiene la lógica de acceso a datos usando AulaRepository.
 */
@Service
public class AulaServiceImpl implements AulaService {

    @Autowired
    private AulaRepository aulaRepository;
    
    @Autowired
    private UsuarioAulaRepository usuarioAulaRepository;


    /**
     * Lista todas las aulas existentes en la base de datos.
     */
    @Override
    public List<Aula> listarAulas() {
        return aulaRepository.findAll();
    }

    /**
     * Devuelve un aula por su ID.
     */
    @Override
    public Optional<Aula> obtenerAulaPorId(Long id) {
        return aulaRepository.findById(id);
    }

    /**
     * Guarda una nueva aula.
     */
    @Override
    public Aula guardarAula(Aula aula) {
        return aulaRepository.save(aula);
    }

    /**
     * Actualiza los datos de un aula.
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
    
    @Override
    public List<AulaVO> listarAulasConEstadoHoy() {
        List<Aula> aulas = aulaRepository.findAll();
        LocalDate hoy = LocalDate.now();

        return aulas.stream().map(aula -> {
            boolean ocupada = usuarioAulaRepository.existsByAula_IdAulaAndFechaReservaAula(aula.getIdAula(), hoy);
            return new AulaVO(
                aula.getIdAula(),
                aula.getNombre(),
                aula.getCapacidad(),
                ocupada ? "ocupado" : "disponible"
            );
        }).collect(Collectors.toList());
    }
    
}
