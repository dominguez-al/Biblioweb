package com.biblioweb.backend.controller;

import com.biblioweb.backend.dto.LoginRequest;
import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.repository.UsuarioRepository;
import com.biblioweb.backend.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.biblioweb.backend.service.CorreoService;
import jakarta.annotation.PostConstruct;



import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Controlador encargado de manejar la autenticación (login).
 * Ruta base: /auth
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository; // Para buscar usuarios por email

    @Autowired
    private PasswordEncoder passwordEncoder; // Para verificar contraseñas encriptadas

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // Utilidad para generar el token JWT
    
    @Autowired
    private CorreoService correoService; // Recuperar la contraseña por correo

    /**
     * Endpoint para autenticación de usuarios.
     * POST /auth/login
     * 
     * @param loginRequest contiene email y password
     * @return token JWT si las credenciales son correctas
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
    	System.out.println("Login con: " + loginRequest.getEmail());
    	System.out.println("Contraseña recibida: " + loginRequest.getPassword());

    	// Busca al usuario por su email
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail());

        // Si no existe o la contraseña no coincide, lanza error
        if (usuario == null || !passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        System.out.println("Hash guardado en BD: " + usuario.getPassword()); 

        // Si es válido, se genera el token JWT con email, rol y nombre
        String token = jwtTokenUtil.generateToken(usuario.getIdUsuario(), usuario.getEmail(), usuario.getRol(), usuario.getNombre());
        System.out.println("Hash guardado en BD: " + usuario.getPassword()); 

        // Se devuelve el token en un Map con clave "token"
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
    
    /**
     * Registro de nuevos usuarios.
     * POST /auth/register
     */
    @PostMapping("/registro")
    public String registro(@RequestBody Usuario nuevoUsuario) {
        System.out.println("Registrando: " + nuevoUsuario.getEmail());

        // Verificamos si ya existe un usuario con ese email
        if (usuarioRepository.findByEmail(nuevoUsuario.getEmail()) != null) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Encriptamos la contraseña antes de guardar
        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));

        // Asignamos rol USER por defecto
        if (nuevoUsuario.getRol() == null || nuevoUsuario.getRol().isBlank()) {
            nuevoUsuario.setRol("USER");
        }


        // Guardamos el usuario en la base de datos
        usuarioRepository.save(nuevoUsuario);

        // 📩 Enviar correo de bienvenida
        correoService.enviarCorreo(
            nuevoUsuario.getEmail(),
            "🎉 Bienvenido a BiblioWeb",
            "Hola " + nuevoUsuario.getNombre() + ", gracias por registrarte en BiblioWeb.\n\nYa podés reservar aulas y libros cuando quieras."
        );

        return "Usuario registrado correctamente";
    }

    
    @PostMapping("/recuperar")
    public String solicitarRecuperacion(@RequestParam String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("No existe usuario con ese email");
        }

        String token = UUID.randomUUID().toString();
        usuario.setResetToken(token);
        usuarioRepository.save(usuario);

        String link = "http://localhost:8080/auth/restablecer?token=" + token;
        correoService.enviarCorreo(
            usuario.getEmail(),
            "Recuperación de contraseña",
            "Haz clic en este enlace para restablecer tu contraseña:\n" + link
        );

        return "Enlace de recuperación enviado al correo";
    }
    
    @PostMapping("/restablecer")
    public String restablecerPassword(@RequestParam String token, @RequestParam String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByResetToken(token);
        if (usuario == null) {
            throw new RuntimeException("Token inválido o expirado");
        }

        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuario.setResetToken(null); // Se elimina el token para que no se reutilice
        usuarioRepository.save(usuario);

        return "Contraseña restablecida correctamente";
    }
    

    
}
