package com.biblioweb.backend.repository;

import com.biblioweb.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interfaz es un componente de acceso a datos
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método personalizado para buscar un usuario por su email
    Usuario findByEmail(String email);
    Usuario findByResetToken(String token);
}
