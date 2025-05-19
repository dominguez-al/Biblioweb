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

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // Endpoints públicos
                .requestMatchers("/auth/**").permitAll()

                // Usuarios
                .requestMatchers("/usuarios/listar").permitAll()

                // Libros
                .requestMatchers("/libros/crear", "/libros/actualizar/**", "/libros/borrar/**").permitAll()//.hasAuthority("ROLE_ADMIN")
                .requestMatchers("/libros/listar", "/libros/ver/**", "/libros/ultimos", "/libros/populares").permitAll()

                // Aulas
                .requestMatchers("/aulas/crear", "/aulas/actualizar/**", "/aulas/borrar/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/aulas/listar", "/aulas/ver/**").permitAll()

                // Reservas (libros y aulas)
                .requestMatchers("/reservas-libros/populares").permitAll()
                .requestMatchers("/reservas-libros/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .requestMatchers("/reservas-aulas/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                // Reseñas
                .requestMatchers("/resenas/todas").permitAll()
                .requestMatchers("/resenas/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


