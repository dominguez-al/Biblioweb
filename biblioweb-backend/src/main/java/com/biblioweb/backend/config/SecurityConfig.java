package com.biblioweb.backend.config;

import com.biblioweb.backend.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
/**
 * Configuración de seguridad para controlar el acceso a los endpoints
 * según el rol del usuario (ADMIN o USER) y validar JWTs.
 */
@Configuration
public class SecurityConfig {

    // Inyección del filtro que valida los tokens JWT en cada petición
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    // Define la cadena de filtros de seguridad de Spring Security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Configuración CORS para permitir peticiones desde el frontend (Angular)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Desactiva la protección CSRF (útil para APIs REST)
            .csrf(csrf -> csrf.disable())
            
            // Configura las reglas de autorización por endpoint
            .authorizeHttpRequests(auth -> auth

                // Endpoints de autenticación: públicos
                .requestMatchers("/auth/**").permitAll()

                // Endpoint para listar usuarios: público
                .requestMatchers("/usuarios/listar").permitAll()

                // Endpoints de libros: todos permitidos públicamente (se pueden proteger si se desea)
                .requestMatchers("/libros/crear", "/libros/actualizar/**", "/libros/borrar/**").permitAll() // .hasAuthority("ROLE_ADMIN")
                .requestMatchers("/libros/listar", "/libros/ver/**", "/libros/ultimos", "/libros/populares").permitAll()

                // Endpoints de aulas: crear/editar/borrar solo para admins, listar/ver son públicos
                .requestMatchers("/aulas/crear", "/aulas/actualizar/**", "/aulas/borrar/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/aulas/listar", "/aulas/ver/**").permitAll()

                // Reservas: consultar populares es público, el resto requiere usuario autenticado
                .requestMatchers("/reservas-libros/populares").permitAll()
                .requestMatchers("/reservas-libros/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .requestMatchers("/reservas-aulas/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                // Reseñas: ver todas es público, las demás requieren autenticación
                .requestMatchers("/resenas/todas").permitAll()
                .requestMatchers("/resenas/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                // Todo lo que no esté especificado requiere autenticación
                .anyRequest().authenticated()
            )
            
            // Inserta el filtro JWT antes del filtro de autenticación por usuario/contraseña
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Devuelve la cadena de seguridad configurada
    }

    // Define configuración CORS para permitir solicitudes del frontend
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200")); // Origen permitido (frontend Angular)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        config.setAllowedHeaders(List.of("*")); // Todos los headers permitidos
        config.setAllowCredentials(true); // Permite enviar cookies o credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica CORS a todos los endpoints
        return source;
    }

    // Bean para obtener el AuthenticationManager, necesario para autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Bean para codificar contraseñas con BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}