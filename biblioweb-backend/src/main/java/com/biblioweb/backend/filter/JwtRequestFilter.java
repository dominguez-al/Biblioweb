package com.biblioweb.backend.filter;

import com.biblioweb.backend.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component // Declara el filtro como un componente de Spring para que sea detectado automáticamente
public class JwtRequestFilter extends OncePerRequestFilter { // Garantiza que el filtro se ejecute una sola vez por solicitud

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // Clase utilitaria para validar y extraer info del JWT

    /**
     * Este método se ejecuta automáticamente en cada solicitud HTTP.
     * Intercepta la petición para verificar si contiene un JWT válido.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Obtiene el encabezado "Authorization" de la solicitud
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Verifica si el header no es nulo y comienza con "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Extrae el token quitando el prefijo "Bearer "

            try {
                // Valida el token
                if (jwtTokenUtil.validateToken(jwt)) {
                    username = jwtTokenUtil.extractUsername(jwt); // Extrae el email del token
                    String role = jwtTokenUtil.extractUserRole(jwt); // Extrae el rol del token

                    // Asegura que el prefijo "ROLE_" esté correctamente formado
                    String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;

                    //Comprobaciones
                    //System.out.println("🟡 JWT role extraído: " + role);
                    //System.out.println("🟢 Seteando autoridad como: " + authority);

                    // Si aún no hay autenticación en el contexto de seguridad, la crea
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        username, // principal (usuario)
                                        null,     // no se necesita contraseña en este punto
                                        Collections.singleton(new SimpleGrantedAuthority(authority)) // autoridad (rol)
                                );

                        // Asocia la autenticación con los detalles de la solicitud actual
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // Establece la autenticación en el contexto de seguridad de Spring
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        System.out.println("🛡️ Contexto autenticado con: " + SecurityContextHolder.getContext().getAuthentication());
                    }
                }
            } catch (Exception ex) {
                // Captura cualquier error durante el procesamiento del token
                System.out.println("⚠️ Error al procesar el token JWT: " + ex.getMessage());
            }
        }

        // Continúa con la cadena de filtros
        chain.doFilter(request, response);
    }
}
