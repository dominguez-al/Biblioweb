package com.biblioweb.backend.mapper;

import com.biblioweb.backend.entity.Resena;
import com.biblioweb.backend.vo.ResenaVO;

/**
 * Mapper para convertir entre Resena (entidad de base de datos)
 * y ResenaVO (objeto de vista utilizado en la capa de presentación).
 */
public class ResenaMapper {

    /**
     * Convierte una entidad Resena en un objeto ResenaVO.
     * Esto se usa para enviar datos al cliente sin exponer toda la estructura interna.
     *
     * @param resena La entidad que viene de la base de datos
     * @return ResenaVO con los campos necesarios para mostrar en frontend
     */
    public static ResenaVO toVO(Resena resena) {
        ResenaVO vo = new ResenaVO();
        vo.setId(resena.getId());
        vo.setComentario(resena.getComentario());
        vo.setPuntuacion(resena.getPuntuacion());
        vo.setFecha(resena.getFecha());
        vo.setUsuarioId(resena.getUsuarioId());

        // Se incluyen datos del libro asociado a la reseña
        vo.setLibroId(resena.getLibro().getIdLibro());
        vo.setTituloLibro(resena.getLibro().getTitulo());

        return vo;
    }

    /**
     * Convierte un ResenaVO recibido del frontend a una entidad Resena.
     * Este método se usa para guardar o actualizar reseñas en la base de datos.
     * Nota: el objeto Libro debe setearse aparte en el servicio, ya que aquí solo se mapea por ID.
     *
     * @param vo El objeto recibido del cliente
     * @return Instancia de Resena sin relación con Libro aún seteada
     */
    public static Resena toEntity(ResenaVO vo) {
        Resena resena = new Resena();
        resena.setId(vo.getId());
        resena.setComentario(vo.getComentario());
        resena.setPuntuacion(vo.getPuntuacion());
        resena.setFecha(vo.getFecha());
        resena.setUsuarioId(vo.getUsuarioId());

        // No se asigna el libro directamente aquí, porque solo tenemos el ID.
        // Esa relación debe completarse en el servicio usando el LibroRepository.

        return resena;
    }
}
