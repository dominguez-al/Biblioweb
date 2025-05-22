package com.biblioweb.backend.repository;

import com.biblioweb.backend.entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Aula.
 * Extiende JpaRepository para heredar métodos CRUD básicos como:
 * - findAll(), findById(), save(), deleteById(), etc.
 *
 * Spring implementará automáticamente la lógica basada en esta interfaz.
 */
@Repository // Indica que esta interfaz es un componente de acceso a datos
public interface AulaRepository extends JpaRepository<Aula, Long> {
    // Puedes agregar métodos personalizados aquí si necesitas consultas específicas
}
