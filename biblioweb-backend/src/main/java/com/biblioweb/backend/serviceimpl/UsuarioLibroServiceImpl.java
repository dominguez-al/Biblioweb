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
import com.biblioweb.backend.vo.LibroPopularVO;
import com.biblioweb.backend.vo.UsuarioLibroVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación de UsuarioLibroService.
 * Gestiona la lógica de negocio relacionada con reservas de libros por parte de usuarios.
 */
@Service
public class UsuarioLibroServiceImpl implements UsuarioLibroService {

    @Autowired
    private UsuarioLibroRepository usuarioLibroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private CorreoService correoService;

    /**
     * Devuelve todas las reservas de libros registradas.
     */
    @Override
    public List<UsuarioLibro> listarReservas() {
        return usuarioLibroRepository.findAll();
    }

    
    
    /**
     * Crea una reserva a partir de un VO:
     * - Busca el usuario y el libro
     * - Mapea el VO a entidad
     * - Guarda la reserva
     */
    @Override
    public UsuarioLibro crearDesdeVO(UsuarioLibroVO vo) {
        Usuario usuario = usuarioRepository.findById(vo.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(vo.getIdLibro())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Verificar si el libro ya está RESERVADO
        if ("RESERVADO".equalsIgnoreCase(libro.getEstado())) {
            throw new RuntimeException("El libro ya está reservado y no se puede reservar.");
        }

        // Cambiar estado del libro a RESERVADO
        libro.setEstado("RESERVADO");
        
        // Antes de guardar el libro
        libro.setTotalReservas(libro.getTotalReservas() + 1);
        libroRepository.save(libro);


        // Crear reserva
        UsuarioLibro reserva = UsuarioLibroMapper.toEntity(vo, usuario, libro);

        // Establecer fechas automáticamente
        reserva.setFechaReservaLibro(LocalDate.now());
        reserva.setFechaDevolucion(LocalDate.now().plusWeeks(2));

        UsuarioLibro reservaGuardada = usuarioLibroRepository.save(reserva);

        // 📩 Enviar correo de confirmación
        correoService.enviarCorreo(
            usuario.getEmail(),
            "Reserva de libro confirmada",
            "Hola " + usuario.getNombre() + ", has reservado el libro \"" + libro.getTitulo() + "\". La fecha de devolución es el " + reserva.getFechaDevolucion() + "."
        );

        return reservaGuardada;
    }
    
    
    
    
    /**
     * Elimina una reserva de libro por su ID.
     */
    @Override
    public void eliminarReserva(Long id) {
        UsuarioLibro reserva = usuarioLibroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Libro libro = reserva.getLibro();
        Usuario usuario = reserva.getUsuario();

        libro.setEstado("DISPONIBLE");
        libroRepository.save(libro);
        usuarioLibroRepository.deleteById(id);

        // 📩 Enviar correo de cancelación
        correoService.enviarCorreo(
            usuario.getEmail(),
            "Reserva cancelada",
            "Hola " + usuario.getNombre() + ", tu reserva del libro \"" + libro.getTitulo() + "\" ha sido cancelada."
        );
    }


    /**
     * Obtiene las reservas activas de un usuario con datos del libro.
     */
    @Override
    public List<UsuarioLibro> obtenerReservasPorUsuario(Long idUsuario) {
        return usuarioLibroRepository.findByUsuarioIdConLibro(idUsuario);
    }
}

