package com.biblioweb.backend.controller;

import com.biblioweb.backend.entity.Aula;
import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.entity.UsuarioAula;
import com.biblioweb.backend.mapper.UsuarioAulaMapper;
import com.biblioweb.backend.repository.AulaRepository;
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

@RestController
@RequestMapping("/reservas-aulas")
public class UsuarioAulaController {

    @Autowired
    private UsuarioAulaService usuarioAulaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AulaRepository aulaRepository;
    
    @Autowired
    private UsuarioAulaRepository usuarioAulaRepository;


    @GetMapping("/listar")
    public List<UsuarioAulaVO> listarReservas() {
        LocalDate hoy = LocalDate.now();

        return usuarioAulaService.listarReservas()
                .stream()
                .filter(reserva -> !reserva.getFechaReservaAula().isBefore(hoy)) // solo hoy o futuras
                .map(UsuarioAulaMapper::toVO)
                .collect(Collectors.toList());
    }

    @PostMapping("/crear")
    public UsuarioAulaVO crearReserva(@RequestBody UsuarioAulaVO vo) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔐 AUTH CONTROLLER: " + auth);

        UsuarioAula reserva = usuarioAulaService.crearDesdeVO(vo);
        return UsuarioAulaMapper.toVO(reserva);
    }



    @GetMapping("/mis-reservas")
    public List<UsuarioAulaVO> obtenerReservasDelUsuario(@RequestParam Long idUsuario) {
        LocalDate hoy = LocalDate.now();

        List<UsuarioAula> reservas = usuarioAulaService.obtenerReservasPorUsuario(idUsuario);

        return reservas.stream()
                       .filter(reserva -> !reserva.getFechaReservaAula().isBefore(hoy)) // 👈 filtra solo actuales o futuras
                       .map(UsuarioAulaMapper::toVO)
                       .collect(Collectors.toList());
    }


    @GetMapping("/existe")
    public ResponseEntity<Boolean> existeReserva(
        @RequestParam Long idUsuario,
        @RequestParam Long idAula,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        boolean existe = usuarioAulaRepository.existsByUsuario_IdUsuarioAndAula_IdAulaAndFechaReservaAula(idUsuario, idAula, fecha);
        return ResponseEntity.ok(existe);
    }

    
 // Controller
    @GetMapping("/fechas-ocupadas/{idAula}")
    public List<LocalDate> obtenerFechasOcupadasPorPath(@PathVariable Long idAula) {
        return usuarioAulaService.obtenerFechasOcupadas(idAula);
    }


    

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

        System.out.println("🔍 ID usuario autenticado: " + usuario.getIdUsuario());
        System.out.println("🔍 ID usuario de la reserva: " + reserva.getUsuario().getIdUsuario());

        
        // Verificar si el usuario autenticado es el dueño de la reserva
        boolean esPropietario = reserva.getUsuario().getIdUsuario().equals(usuario.getIdUsuario());

        // Verificar si tiene rol ADMIN
        boolean esAdmin = SecurityContextHolder.getContext().getAuthentication()
            .getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        // Si no es ni dueño ni admin, se bloquea
        if (!esPropietario && !esAdmin) {
            throw new RuntimeException("No autorizado para eliminar esta reserva");
        }

        // Ejecutar eliminación
        usuarioAulaService.eliminarReserva(id);
    }


}
