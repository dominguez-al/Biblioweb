package com.biblioweb.backend.repository;

import com.biblioweb.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Usuario.
 * Proporciona acceso a operaciones CRUD y métodos personalizados
 * para autenticación y recuperación de cuenta.
 */
@Repository // Marca esta interfaz como un componente Spring para acceso a datos
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su email.
     * Se usa comúnmente para login, registro y verificación de duplicados.
     *
     * @param email Email del usuario
     * @return Usuario asociado a ese email, o null si no existe
     */
    Usuario findByEmail(String email);

    /**
     * Busca un usuario por su token de recuperación de contraseña.
     * Se utiliza durante el proceso de restablecimiento de contraseña.
     *
     * @param token Token único generado para recuperación
     * @return Usuario asociado al token, o null si no es válido
     */
    Usuario findByResetToken(String token);
}
