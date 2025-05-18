package com.biblioweb.backend.service;

import com.biblioweb.backend.vo.ResenaVO;
import java.util.List;

public interface ResenaService {

    List<ResenaVO> obtenerPorLibro(Long libroId);

    List<ResenaVO> obtenerTodas();

    ResenaVO guardar(ResenaVO vo);
}
