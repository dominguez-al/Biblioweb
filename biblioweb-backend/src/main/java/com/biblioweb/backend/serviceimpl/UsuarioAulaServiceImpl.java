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
 * Implementación del servicio para la gestión de reservas de aulas por parte de usuarios.
 * Esta clase contiene la lógica de negocio para:
 * - Listar y obtener reservas.
 * - Crear reservas (tanto a partir de una entidad como a partir de un VO).
 * - Eliminar reservas y realizar acciones de notificación.
 * - Consultar fechas ocupadas y reservas por usuario.
 */
@Service
public class UsuarioAulaServiceImpl implements UsuarioAulaService {

    @Autowired
    private UsuarioAulaRepository usuarioAulaRepository; // Acceso a datos para reservas de aula

    @Autowired
    private UsuarioRepository usuarioRepository; // Acceso a datos de usuarios

    @Autowired
    private AulaRepository aulaRepository; // Acceso a datos de aulas

    @Autowired
    private CorreoService correoService; // Servicio para el envío de notificaciones por correo

    /**
     * Obtiene todas las reservas de aula registradas en la base de datos.
     * @return Lista de todas las reservas (entidades UsuarioAula)
     */
    @Override
    public List<UsuarioAula> listarReservas() {
        return usuarioAulaRepository.findAll();
    }

    /**
     * Busca una reserva específica por su ID.
     * @param id ID de la reserva
     * @return Optional que contiene la reserva si se encuentra o estará vacío si no existe
     */
    @Override
    public Optional<UsuarioAula> obtenerReservaPorId(Long id) {
        return usuarioAulaRepository.findById(id);
    }

    /**
     * Crea una reserva a partir de un objeto de vista (VO).
     * Se valida la existencia del usuario y del aula mediante sus respectivos repositorios.
     * Luego se construye la entidad UsuarioAula y se delega la creación en el método crearReserva.
     *
     * @param vo UsuarioAulaVO que contiene los datos enviados desde el frontend
     * @return La reserva creada (entidad UsuarioAula) tras aplicar la validación y transformación
     */
    @Override
    public UsuarioAula crearDesdeVO(UsuarioAulaVO vo) {
        // Se busca el usuario por su ID; si no se encuentra, se lanza una excepción
        var usuario = usuarioRepository.findById(vo.getIdUsuario())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Se busca el aula por su ID; si no se encuentra, se lanza una excepción
        var aula = aulaRepository.findById(vo.getIdAula())
                     .orElseThrow(() -> new RuntimeException("Aula no encontrada"));

        // Se crea una nueva instancia de UsuarioAula y se asignan los datos obtenidos
        UsuarioAula reserva = new UsuarioAula();
        reserva.setUsuario(usuario);
        reserva.setAula(aula);
        reserva.setFechaReservaAula(vo.getFechaReservaAula());
        reserva.setFechaDevolucionAula(vo.getFechaDevolucionAula());

        // Se delega la creación con validación utilizando el método crearReserva
        return crearReserva(reserva);
    }

    /**
     * Guarda una nueva reserva en la base de datos sin validaciones adicionales.
     * Este método puede usarse para pruebas o para operaciones donde ya se han realizado
     * las validaciones de forma externa.
     *
     * @param reserva Objeto UsuarioAula a guardar
     * @return Reserva guardada (entidad con ID asignado)
     */
    @Override
    public UsuarioAula guardarReserva(UsuarioAula reserva) {
        return usuarioAulaRepository.save(reserva);
    }

    /**
     * Elimina una reserva por su ID y realiza las siguientes acciones:
     * - Elimina la reserva de la base de datos.
     * - Cambia el estado del aula asociada a "DISPONIBLE".
     * - Envía un correo al usuario notificando la cancelación.
     *
     * @param id ID de la reserva a eliminar.
     */
    @Override
    public void eliminarReserva(Long id) {
        usuarioAulaRepository.findById(id).ifPresent(reserva -> {
            // Elimina la reserva del repositorio
            usuarioAulaRepository.deleteById(id);

            // Cambia el estado del aula a "DISPONIBLE" y guarda el cambio
            Aula aula = reserva.getAula();
            aula.setEstado("DISPONIBLE");
            aulaRepository.save(aula);

            // Envía un correo de notificación al usuario informándole de la cancelación
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
     * Crea una nueva reserva de aula asegurándose de que el usuario no tenga ya una reserva
     * para el mismo aula y la misma fecha. Si la reserva ya existe, lanza un error con estado BAD_REQUEST.
     * Además, se actualiza el estado del aula a "OCUPADO" y se envía un correo de confirmación al usuario.
     *
     * @param reserva Objeto UsuarioAula que contiene la reserva a crear
     * @return La reserva creada y guardada (con ID asignado)
     */
    @Override
    public UsuarioAula crearReserva(UsuarioAula reserva) {
        // Verifica que el usuario no tenga una reserva previa para la misma aula y fecha
        boolean existe = usuarioAulaRepository.existsByUsuario_IdUsuarioAndAula_IdAulaAndFechaReservaAula(
            reserva.getUsuario().getIdUsuario(),
            reserva.getAula().getIdAula(),
            reserva.getFechaReservaAula()
        );

        // Si ya existe, se lanza una excepción de BAD_REQUEST (400)
        if (existe) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya tienes una reserva para ese día.");
        }

        // Se asigna la misma fecha de reserva como fecha de devolución (puedes ajustar esta lógica)
        reserva.setFechaDevolucionAula(reserva.getFechaReservaAula());

        // Actualiza el estado del aula a "OCUPADO"
        Aula aula = reserva.getAula();
        aula.setEstado("OCUPADO");
        aulaRepository.save(aula);

        // Guarda la reserva en la base de datos
        UsuarioAula guardada = usuarioAulaRepository.save(reserva);

        // Envía un correo al usuario para confirmar la reserva
        correoService.enviarCorreo(
            reserva.getUsuario().getEmail(),
            "Reserva de aula confirmada",
            "Has reservado el aula \"" + aula.getNombre() +
            "\" para el día " + reserva.getFechaReservaAula() + "."
        );

        return guardada;
    }

    /**
     * Devuelve una lista de fechas en las que el aula está ocupada.
     * Esta información es útil para deshabilitar fechas en un calendario del frontend.
     *
     * @param idAula ID del aula
     * @return Lista de fechas (LocalDate) que ya tienen reserva
     */
    @Override
    public List<LocalDate> obtenerFechasOcupadas(Long idAula) {
        return usuarioAulaRepository.findFechasReservadasPorAula(idAula);
    }

    /**
     * Devuelve todas las reservas asociadas a un usuario específico.
     *
     * @param idUsuario ID del usuario
     * @return Lista de reservas (UsuarioAula) realizadas por el usuario
     */
    @Override
    public List<UsuarioAula> obtenerReservasPorUsuario(Long idUsuario) {
        return usuarioAulaRepository.findByUsuario_IdUsuario(idUsuario);
    }
}

