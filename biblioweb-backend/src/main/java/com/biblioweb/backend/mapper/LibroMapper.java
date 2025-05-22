package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Libro;
import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.vo.LibroVO;

/**
 * Mapper para transformar entre la entidad Libro y su representación de vista LibroVO.
 * Permite mantener separadas las capas de datos y presentación.
 */
public class LibroMapper {

    /**
     * Convierte una entidad Libro en un objeto de vista LibroVO.
     * Este método se usa para exponer datos al frontend.
     *
     * @param libro La entidad Libro tomada desde la base de datos
     * @return El objeto LibroVO que será enviado al cliente
     */
    public static LibroVO toVO(Libro libro) {
        LibroVO vo = new LibroVO();

        // Asignación de campos básicos
        vo.setIdLibro(libro.getIdLibro());
        vo.setTitulo(libro.getTitulo());
        vo.setAutor(libro.getAutor());
        vo.setGenero(libro.getGenero());
        vo.setEstado(libro.getEstado());
        vo.setImagen(libro.getImagen());
        vo.setTotalReservas(libro.getTotalReservas());

        // Conversión de la fecha de alta a String (formato legible para el cliente)
        if (libro.getFechaAltaLibro() != null) {
            vo.setFechaAltaLibro(libro.getFechaAltaLibro().toString()); // formato ISO (YYYY-MM-DD)
        } else {
            vo.setFechaAltaLibro(""); // alternativa: null o "N/A"
        }

        // Debug en consola para saber cuántas reservas tiene el libro
        System.out.println("Reservas del libro " + libro.getTitulo() + ": " +
            (libro.getReservas() != null ? libro.getReservas().size() : 0));

        // Si el libro está en estado "RESERVADO", mostramos quién lo reservó
        if (libro.getReservas() != null &&
            !libro.getReservas().isEmpty() &&
            "RESERVADO".equalsIgnoreCase(libro.getEstado())) {

            // Se toma el primer usuario que reservó el libro (se asume que hay solo uno activo)
            Usuario usuario = libro.getReservas().get(0).getUsuario();
            if (usuario != null) {
                vo.setReservadoPor(usuario.getEmail()); // Alternativa: usuario.getNombre()
            }
        }

        return vo;
    }

    /**
     * Convierte un LibroVO recibido del cliente en una entidad Libro.
     * Se usa para crear o actualizar libros en la base de datos.
     *
     * @param vo El objeto recibido desde el frontend
     * @return Una nueva instancia de Libro con los datos del VO
     */
    public static Libro toEntity(LibroVO vo) {
        Libro libro = new Libro();

        // Asignación de campos básicos
        libro.setIdLibro(vo.getIdLibro());
        libro.setTitulo(vo.getTitulo());
        libro.setAutor(vo.getAutor());
        libro.setGenero(vo.getGenero());
        libro.setEstado(vo.getEstado());

        // No se asigna fechaAltaLibro manualmente; se maneja con @PrePersist en la entidad
        
        libro.setImagen(vo.getImagen());

        return libro;
    }
}

