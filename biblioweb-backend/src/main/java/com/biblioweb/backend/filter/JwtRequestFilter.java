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

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                if (jwtTokenUtil.validateToken(jwt)) {
                    username = jwtTokenUtil.extractUsername(jwt);
                    String role = jwtTokenUtil.extractUserRole(jwt);

                    // ✅ CORREGIDO: No agregar "ROLE_" dos veces
                    String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;

                    System.out.println("🟡 JWT role extraído: " + role);
                    System.out.println("🟢 Seteando autoridad como: " + authority);

                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        username,
                                        null,
                                        Collections.singleton(new SimpleGrantedAuthority(authority))
                                );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        System.out.println("🛡️ Contexto autenticado con: " + SecurityContextHolder.getContext().getAuthentication());
                    }
                }
            } catch (Exception ex) {
                System.out.println("⚠️ Error al procesar el token JWT: " + ex.getMessage());
            }
        }

        chain.doFilter(request, response);

    }
}
