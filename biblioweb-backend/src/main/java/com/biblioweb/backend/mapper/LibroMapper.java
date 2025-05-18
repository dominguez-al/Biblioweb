package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Libro;
import com.biblioweb.backend.entity.Usuario;
import com.biblioweb.backend.vo.LibroVO;



/**
 * Mapper para transformar entre Libro y LibroVO
 */
public class LibroMapper {

    // Convierte una entidad Libro en un VO
    public static LibroVO toVO(Libro libro) {
        LibroVO vo = new LibroVO();
        vo.setIdLibro(libro.getIdLibro());
        vo.setTitulo(libro.getTitulo());
        vo.setAutor(libro.getAutor());
        vo.setGenero(libro.getGenero());
        vo.setEstado(libro.getEstado());
        vo.setImagen(libro.getImagen());
        vo.setTotalReservas(libro.getTotalReservas());
        if (libro.getFechaAltaLibro() != null) {
            vo.setFechaAltaLibro(libro.getFechaAltaLibro().toString());
        } else {
            vo.setFechaAltaLibro(""); // o "N/A", o null si prefieres
        }
        System.out.println("Reservas del libro " + libro.getTitulo() + ": " +
        	    (libro.getReservas() != null ? libro.getReservas().size() : 0));

        if (libro.getReservas() != null && !libro.getReservas().isEmpty() && "RESERVADO".equalsIgnoreCase(libro.getEstado())) {
            Usuario usuario = libro.getReservas().get(0).getUsuario(); // solo el primero
            if (usuario != null) {
                vo.setReservadoPor(usuario.getEmail()); // o usuario.getNombre(), como prefieras
            }
        }
        return vo;
    }

    // Convierte un VO en una entidad Libro
    public static Libro toEntity(LibroVO vo) {
        Libro libro = new Libro();
        libro.setIdLibro(vo.getIdLibro());
        libro.setTitulo(vo.getTitulo());
        libro.setAutor(vo.getAutor());
        libro.setGenero(vo.getGenero());
        libro.setEstado(vo.getEstado());
        // fechaAltaLibro se inicializa automáticamente con @PrePersist
        libro.setImagen(vo.getImagen());
        return libro;
    }
}
