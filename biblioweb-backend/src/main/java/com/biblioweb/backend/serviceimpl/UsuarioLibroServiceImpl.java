package com.biblioweb.backend.serviceimpl;

import com.biblioweb.backend.entity.Libro;
import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.entity.UsuarioLibro;
import com.biblioweb.backend.mapper.UsuarioLibroMapper;
import com.biblioweb.backend.repository.LibroRepository;
import com.biblioweb.backend.repository.UsuarioLibroRepository;
import com.biblioweb.backend.repository.UsuarioRepository;
import com.biblioweb.backend.service.CorreoService;
import com.biblioweb.backend.service.UsuarioLibroService;
import com.biblioweb.backend.vo.UsuarioLibroVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación de UsuarioLibroService.
 * Contiene la lógica de negocio para la gestión de reservas de libros por parte de usuarios.
 */
@Service
public class UsuarioLibroServiceImpl implements UsuarioLibroService {

    @Autowired
    private UsuarioLibroRepository usuarioLibroRepository; // Acceso a datos de reservas de libros

    @Autowired
    private UsuarioRepository usuarioRepository; // Acceso a datos de usuarios

    @Autowired
    private LibroRepository libroRepository; // Acceso a datos de libros

    @Autowired
    private CorreoService correoService; // Envío de correos al usuario

    /**
     * Devuelve todas las reservas de libros registradas en la base de datos.
     * Usado principalmente en administración o para debug.
     */
    @Override
    public List<UsuarioLibro> listarReservas() {
        return usuarioLibroRepository.findAll();
    }

    /**
     * Crea una nueva reserva de libro a partir de un objeto VO.
     * - Verifica que el libro no esté ya reservado
     * - Marca el libro como RESERVADO
     * - Incrementa el contador de reservas
     * - Establece fechas de préstamo y devolución
     * - Guarda la reserva
     * - Notifica por correo al usuario
     *
     * @param vo Objeto de vista que contiene la info de la reserva
     * @return Reserva guardada (entidad)
     */
    @Override
    public UsuarioLibro crearDesdeVO(UsuarioLibroVO vo) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(vo.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar libro
        Libro libro = libroRepository.findById(vo.getIdLibro())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Validar disponibilidad
        if ("RESERVADO".equalsIgnoreCase(libro.getEstado())) {
            throw new RuntimeException("El libro ya está reservado y no se puede reservar.");
        }

        // Marcar como reservado
        libro.setEstado("RESERVADO");

        // Incrementar el contador de reservas del libro
        libro.setTotalReservas(libro.getTotalReservas() + 1);
        libroRepository.save(libro);

        // Crear entidad UsuarioLibro a partir del VO
        UsuarioLibro reserva = UsuarioLibroMapper.toEntity(vo, usuario, libro);

        // Establecer fecha actual y devolución a 2 semanas
        reserva.setFechaReservaLibro(LocalDate.now());
        reserva.setFechaDevolucion(LocalDate.now().plusWeeks(2));

        // Guardar reserva
        UsuarioLibro reservaGuardada = usuarioLibroRepository.save(reserva);

        // Enviar confirmación por correo
        correoService.enviarCorreo(
            usuario.getEmail(),
            "Reserva de libro confirmada",
            "Hola " + usuario.getNombre() +
            ", has reservado el libro \"" + libro.getTitulo() +
            "\". La fecha de devolución es el " + reserva.getFechaDevolucion() + "."
        );

        return reservaGuardada;
    }

    /**
     * Elimina una reserva de libro por su ID.
     * - Cambia el estado del libro a DISPONIBLE
     * - Elimina la reserva
     * - Envía correo de cancelación
     *
     * @param id ID de la reserva a eliminar
     */
    @Override
    public void eliminarReserva(Long id) {
        UsuarioLibro reserva = usuarioLibroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Libro libro = reserva.getLibro();
        Usuario usuario = reserva.getUsuario();

        // Restaurar estado del libro
        libro.setEstado("DISPONIBLE");
        libroRepository.save(libro);

        // Eliminar reserva
        usuarioLibroRepository.deleteById(id);

        // Enviar correo de notificación
        correoService.enviarCorreo(
            usuario.getEmail(),
            "Reserva cancelada",
            "Hola " + usuario.getNombre() +
            ", tu reserva del libro \"" + libro.getTitulo() + "\" ha sido cancelada."
        );
    }

    /**
     * Obtiene todas las reservas de libros de un usuario, incluyendo los datos del libro.
     * Usa un JOIN FETCH en el repositorio para evitar el problema de lazy loading.
     *
     * @param idUsuario ID del usuario
     * @return Lista de reservas del usuario
     */
    @Override
    public List<UsuarioLibro> obtenerReservasPorUsuario(Long idUsuario) {
        return usuarioLibroRepository.findByUsuarioIdConLibro(idUsuario);
    }
}

