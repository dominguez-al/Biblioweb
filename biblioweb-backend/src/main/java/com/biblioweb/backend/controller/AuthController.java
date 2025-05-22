package com.biblioweb.backend.controller;

import com.biblioweb.backend.dto.LoginRequest;
import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.repository.UsuarioRepository;
import com.biblioweb.backend.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.biblioweb.backend.service.CorreoService;




import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Controlador encargado de manejar la autenticación (login, registro, recuperación de contraseña).
 * Ruta base: /auth
 */
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde el frontend Angular
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositorio para acceder a los usuarios

    @Autowired
    private PasswordEncoder passwordEncoder; // Codificador de contraseñas (BCrypt)

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // Utilidad para crear tokens JWT
    
    @Autowired
    private CorreoService correoService; // Servicio para enviar correos

    /**
     * Endpoint para autenticar al usuario (login).
     * POST /auth/login
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        // Imprime en consola los datos recibidos (útil para debug)
        System.out.println("Login con: " + loginRequest.getEmail());
        System.out.println("Contraseña recibida: " + loginRequest.getPassword());

        // Busca al usuario por su email
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail());

        // Si no existe el usuario o la contraseña no coincide, lanza error
        if (usuario == null || !passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        System.out.println("Hash guardado en BD: " + usuario.getPassword());

        // Genera un token JWT con los datos del usuario
        String token = jwtTokenUtil.generateToken(
            usuario.getIdUsuario(), 
            usuario.getEmail(), 
            usuario.getRol(), 
            usuario.getNombre()
        );

        // Devuelve el token dentro de un Map
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
    
    /**
     * Endpoint para registrar un nuevo usuario.
     * POST /auth/registro
     */
    @PostMapping("/registro")
    public String registro(@RequestBody Usuario nuevoUsuario) {
        System.out.println("Registrando: " + nuevoUsuario.getEmail());

        // Verifica si ya existe un usuario con ese email
        if (usuarioRepository.findByEmail(nuevoUsuario.getEmail()) != null) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Encripta la contraseña antes de guardar
        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));

        // Si no tiene rol definido, asigna "USER" por defecto
        if (nuevoUsuario.getRol() == null || nuevoUsuario.getRol().isBlank()) {
            nuevoUsuario.setRol("USER");
        }

        // Guarda el usuario en la base de datos
        usuarioRepository.save(nuevoUsuario);

        // Envía un correo de bienvenida
        correoService.enviarCorreo(
            nuevoUsuario.getEmail(),
            "🎉 Bienvenido a BiblioWeb",
            "Hola " + nuevoUsuario.getNombre() + 
            ", gracias por registrarte en BiblioWeb.\n\nYa podés reservar aulas y libros cuando quieras."
        );

        return "Usuario registrado correctamente";
    }

    /**
     * Endpoint para iniciar el proceso de recuperación de contraseña.
     * POST /auth/recuperar
     */
    @PostMapping("/recuperar")
    public String solicitarRecuperacion(@RequestParam String email) {
        // Busca al usuario por su email
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("No existe usuario con ese email");
        }

        // Genera un token único para recuperación
        String token = UUID.randomUUID().toString();
        usuario.setResetToken(token);
        usuarioRepository.save(usuario);

        // Crea un link de recuperación (puede cambiar según frontend/backend)
        String link = "http://localhost:8080/auth/restablecer?token=" + token;

        // Envía el correo con el enlace
        correoService.enviarCorreo(
            usuario.getEmail(),
            "Recuperación de contraseña",
            "Haz clic en este enlace para restablecer tu contraseña:\n" + link
        );

        return "Enlace de recuperación enviado al correo";
    }

    /**
     * Endpoint para restablecer la contraseña mediante el token enviado por correo.
     * POST /auth/restablecer
     */
    @PostMapping("/restablecer")
    public String restablecerPassword(@RequestParam String token, @RequestParam String nuevaPassword) {
        // Busca al usuario por el token de recuperación
        Usuario usuario = usuarioRepository.findByResetToken(token);
        if (usuario == null) {
            throw new RuntimeException("Token inválido o expirado");
        }

        // Actualiza la contraseña (encriptada) y limpia el token
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuario.setResetToken(null); // Se elimina el token para que no se reutilice
        usuarioRepository.save(usuario);

        return "Contraseña restablecida correctamente";
    }
}