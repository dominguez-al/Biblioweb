package com.biblioweb.backend.serviceimpl;

import com.biblioweb.backend.entity.Aula;
import com.biblioweb.backend.entity.UsuarioAula;
import com.biblioweb.backend.repository.AulaRepository;
import com.biblioweb.backend.repository.UsuarioAulaRepository;
import com.biblioweb.backend.repository.UsuarioRepository;
import com.biblioweb.backend.service.CorreoService;
import com.biblioweb.backend.service.UsuarioAulaService;
import com.biblioweb.backend.vo.UsuarioAulaVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de UsuarioAulaService.
 * Maneja la lógica de negocio para reservas de aulas por parte de usuarios.
 */
@Service
public class UsuarioAulaServiceImpl implements UsuarioAulaService {

    @Autowired
    private UsuarioAulaRepository usuarioAulaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private CorreoService correoService;
    
    

    /**
     * Obtiene todas las reservas de aula existentes.
     */
    @Override
    public List<UsuarioAula> listarReservas() {
        return usuarioAulaRepository.findAll();
    }

    
    
    /**
     * Busca una reserva específica por su ID.
     */
    @Override
    public Optional<UsuarioAula> obtenerReservaPorId(Long id) {
        return usuarioAulaRepository.findById(id);
    }

    
    
    
    public UsuarioAula crearDesdeVO(UsuarioAulaVO vo) {
        var usuario = usuarioRepository.findById(vo.getIdUsuario())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        var aula = aulaRepository.findById(vo.getIdAula())
                     .orElseThrow(() -> new RuntimeException("Aula no encontrada"));

        UsuarioAula reserva = new UsuarioAula();
        reserva.setUsuario(usuario);
        reserva.setAula(aula);
        reserva.setFechaReservaAula(vo.getFechaReservaAula());
        reserva.setFechaDevolucionAula(vo.getFechaDevolucionAula());

        return crearReserva(reserva); // usa la lógica de validación ya existente
    }

    
    
    /**
     * Guarda una nueva reserva directamente.
     */
    @Override
    public UsuarioAula guardarReserva(UsuarioAula reserva) {
        return usuarioAulaRepository.save(reserva);
    }

    
    
    
    
    /**
     * Elimina una reserva y realiza las acciones necesarias:
     * - Cambia el estado del aula a "DISPONIBLE"
     * - Envía correos al usuario notificando la cancelación
     */
    @Override
    public void eliminarReserva(Long id) {
        usuarioAulaRepository.findById(id).ifPresent(reserva -> {
            usuarioAulaRepository.deleteById(id);

            Aula aula = reserva.getAula();
            aula.setEstado("DISPONIBLE");
            aulaRepository.save(aula);

            correoService.enviarCorreo(
                reserva.getUsuario().getEmail(),
                "Reserva de aula cancelada",
                "Hola " + reserva.getUsuario().getNombre() + ",\n\nTu reserva del aula \"" +
                aula.getNombre() + "\" para el día " + reserva.getFechaReservaAula() +
                " ha sido cancelada correctamente."
            );
        });
    }

    /**
     * Crea una nueva reserva asegurándose de que el usuario no tenga ya una en la misma fecha.
     * Cambia el estado del aula a "OCUPADA" y envía correo de confirmación.
     */
    
    @Override
    public UsuarioAula crearReserva(UsuarioAula reserva) {
    	boolean existe = usuarioAulaRepository.existsByUsuario_IdUsuarioAndAula_IdAulaAndFechaReservaAula(
    		    reserva.getUsuario().getIdUsuario(),
    		    reserva.getAula().getIdAula(),
    		    reserva.getFechaReservaAula()
    		);

        if (existe) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya tienes una reserva para ese día.");
        }

        // Asigna misma fecha a devolución
        reserva.setFechaDevolucionAula(reserva.getFechaReservaAula());

        Aula aula = reserva.getAula();
        aulaRepository.save(aula);

        UsuarioAula guardada = usuarioAulaRepository.save(reserva);

        correoService.enviarCorreo(
            reserva.getUsuario().getEmail(),
            "Reserva de aula confirmada",
            "Has reservado el aula \"" + aula.getNombre() +
            "\" para el día " + reserva.getFechaReservaAula() + "."
        );

        return guardada;
    }

    
    @Override
    public List<LocalDate> obtenerFechasOcupadas(Long idAula) {
        return usuarioAulaRepository.findFechasReservadasPorAula(idAula);
    }

    
    
    @Override
    public List<UsuarioAula> obtenerReservasPorUsuario(Long idUsuario) {
        return usuarioAulaRepository.findByUsuario_IdUsuario(idUsuario);
    }
    
}
