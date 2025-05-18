package com.biblioweb.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Clase utilitaria para manejar la creación, validación y extracción de datos de tokens JWT.
 */
@Component
public class JwtTokenUtil {

    // Clave secreta para firmar el token (mínimo 32 bytes para HS256)
    private final Key SECRET_KEY = Keys.hmacShaKeyFor("biblioweb_super_secure_key_123456".getBytes());

    //  Duración del token: 10 horas
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    /**
     * Extrae el nombre de usuario (email) del token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la fecha de expiración del token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae cualquier claim del token usando una función.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Verifica si el token ha expirado.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * ✅ Genera un token JWT con datos personalizados del usuario.
     */
    public String generateToken(Long userId, String username, String role, String nombre) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("idUsuario", userId);
        claims.put("role", role.replace("ROLE_", "")); 
        claims.put("nombre", nombre);
        return createToken(claims, username);
    }

    /**
     * Crea el token JWT a partir de los claims y el sujeto (email).
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida si el token es correcto y no ha expirado.
     */
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    /**
     * Valida un token sin necesidad de usuario (solo verifica firma y expiración).
     */
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extrae el rol del usuario desde el token.
     */
    public String extractUserRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
}

