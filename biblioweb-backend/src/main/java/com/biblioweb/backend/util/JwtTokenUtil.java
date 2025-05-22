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

    // Clave secreta usada para firmar los tokens JWT (mínimo 256 bits para HS256)
    private final Key SECRET_KEY = Keys.hmacShaKeyFor("biblioweb_super_secure_key_123456".getBytes());

    // Duración del token (10 horas en milisegundos)
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    /**
     * Extrae el nombre de usuario (generalmente el email) desde el token.
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
     * Permite extraer cualquier dato (claim) del token con una función de transformación.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims (información embebida) del token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Firma usada para validar integridad
                .build()
                .parseClaimsJws(token) // Lanza excepción si el token es inválido o corrupto
                .getBody();
    }

    /**
     * Verifica si el token ya ha expirado.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * ✅ Genera un token JWT con información del usuario (id, nombre, rol, email).
     * Este método se llama después de validar el login correctamente.
     */
    public String generateToken(Long userId, String username, String role, String nombre) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("idUsuario", userId);
        claims.put("role", role.replace("ROLE_", "")); // Limpieza: evita doble prefijo ROLE_
        claims.put("nombre", nombre);
        return createToken(claims, username);
    }

    /**
     * Crea el token JWT firmado y con fecha de expiración.
     *
     * @param claims información adicional como id, rol, etc.
     * @param subject el 'usuario' principal (usualmente el email)
     * @return token JWT firmado y serializado
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Información personalizada
                .setSubject(subject) // Identificador principal (email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiración
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Firma con algoritmo HS256
                .compact(); // Serializa el JWT como String
    }

    /**
     * Valida si el token corresponde al usuario esperado y no ha expirado.
     */
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    /**
     * Valida que el token no esté expirado ni alterado (sin comparar username).
     * Útil en filtros que no requieren identidad explícita.
     */
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false; // Token inválido o manipulado
        }
    }

    /**
     * Extrae el rol (perfil) del usuario desde los claims del token.
     */
    public String extractUserRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
}

