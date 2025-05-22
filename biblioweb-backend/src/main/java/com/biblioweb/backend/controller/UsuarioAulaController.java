package com.biblioweb.backend.controller;


import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.entity.UsuarioAula;
import com.biblioweb.backend.mapper.UsuarioAulaMapper;
import com.biblioweb.backend.repository.UsuarioAulaRepository;
import com.biblioweb.backend.repository.UsuarioRepository;
import com.biblioweb.backend.service.UsuarioAulaService;
import com.biblioweb.backend.vo.UsuarioAulaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController // Declara este controlador como REST
@RequestMapping("/reservas-aulas") // Prefijo para todas las rutas relacionadas con reservas de aulas
public class UsuarioAulaController {

    @Autowired
    private UsuarioAulaService usuarioAulaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioAulaRepository usuarioAulaRepository;

    /**
     * GET /reservas-aulas/listar
     * Lista todas las reservas de aulas que son para hoy o fechas futuras.
     */
    @GetMapping("/listar")
    public List<UsuarioAulaVO> listarReservas() {
        LocalDate hoy = LocalDate.now();

        return usuarioAulaService.listarReservas()
                .stream()
                .filter(reserva -> !reserva.getFechaReservaAula().isBefore(hoy)) // Filtra solo reservas actuales o futuras
                .map(UsuarioAulaMapper::toVO)
                .collect(Collectors.toList());
    }

    /**
     * POST /reservas-aulas/crear
     * Crea una nueva reserva de aula.
     */
    @PostMapping("/crear")
    public UsuarioAulaVO crearReserva(@RequestBody UsuarioAulaVO vo) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔐 AUTH CONTROLLER: " + auth); // Debug: muestra el usuario autenticado

        UsuarioAula reserva = usuarioAulaService.crearDesdeVO(vo); // Crea reserva desde VO
        return UsuarioAulaMapper.toVO(reserva); // Devuelve el VO resultante
    }

    /**
     * GET /reservas-aulas/mis-reservas?idUsuario=#
     * Devuelve solo las reservas activas (hoy o futuras) de un usuario.
     */
    @GetMapping("/mis-reservas")
    public List<UsuarioAulaVO> obtenerReservasDelUsuario(@RequestParam Long idUsuario) {
        LocalDate hoy = LocalDate.now();

        List<UsuarioAula> reservas = usuarioAulaService.obtenerReservasPorUsuario(idUsuario);

        return reservas.stream()
                       .filter(reserva -> !reserva.getFechaReservaAula().isBefore(hoy)) // Filtra por fecha
                       .map(UsuarioAulaMapper::toVO)
                       .collect(Collectors.toList());
    }

    /**
     * GET /reservas-aulas/existe?idUsuario=...&idAula=...&fecha=...
     * Verifica si ya existe una reserva para ese usuario, aula y fecha.
     */
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existeReserva(
        @RequestParam Long idUsuario,
        @RequestParam Long idAula,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        boolean existe = usuarioAulaRepository.existsByUsuario_IdUsuarioAndAula_IdAulaAndFechaReservaAula(idUsuario, idAula, fecha);
        return ResponseEntity.ok(existe);
    }

    /**
     * GET /reservas-aulas/fechas-ocupadas/{idAula}
     * Devuelve una lista de fechas ya reservadas para un aula.
     */
    @GetMapping("/fechas-ocupadas/{idAula}")
    public List<LocalDate> obtenerFechasOcupadasPorPath(@PathVariable Long idAula) {
        return usuarioAulaService.obtenerFechasOcupadas(idAula);
    }

    /**
     * DELETE /reservas-aulas/borrar/{id}
     * Elimina una reserva si el usuario autenticado es el propietario o tiene rol ADMIN.
     */
    @DeleteMapping("/borrar/{id}")
    public void eliminarReserva(@PathVariable Long id) {
        // Obtener el correo del usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar el usuario por su email
        Usuario usuario = Optional.ofNullable(usuarioRepository.findByEmail(email))
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar la reserva por ID
        Optional<UsuarioAula> reservaOpt = usuarioAulaService.obtenerReservaPorId(id);
        if (reservaOpt.isEmpty()) {
            throw new RuntimeException("Reserva no encontrada");
        }

        UsuarioAula reserva = reservaOpt.get();

        // Mostrar en consola para depuración
        System.out.println("🔍 ID usuario autenticado: " + usuario.getIdUsuario());
        System.out.println("🔍 ID usuario de la reserva: " + reserva.getUsuario().getIdUsuario());

        // Validar si el usuario autenticado es el dueño de la reserva
        boolean esPropietario = reserva.getUsuario().getIdUsuario().equals(usuario.getIdUsuario());

        // Validar si el usuario es administrador
        boolean esAdmin = SecurityContextHolder.getContext().getAuthentication()
            .getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        // Solo el dueño o un admin pueden eliminar la reserva
        if (!esPropietario && !esAdmin) {
            throw new RuntimeException("No autorizado para eliminar esta reserva");
        }

        // Ejecutar la eliminación
        usuarioAulaService.eliminarReserva(id);
    }
}
