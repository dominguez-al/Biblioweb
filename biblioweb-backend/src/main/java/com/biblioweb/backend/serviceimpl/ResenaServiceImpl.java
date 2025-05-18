package com.biblioweb.backend.serviceimpl;

import com.biblioweb.backend.entity.Resena;
import com.biblioweb.backend.repository.ResenaRepository;
import com.biblioweb.backend.service.ResenaService;
import com.biblioweb.backend.vo.ResenaVO;
import com.biblioweb.backend.mapper.ResenaMapper;
import com.biblioweb.backend.entity.Libro;
import com.biblioweb.backend.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResenaServiceImpl implements ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<ResenaVO> obtenerPorLibro(Long libroId) {
        return resenaRepository.findByLibro_IdLibro(libroId)
                .stream()
                .map(ResenaMapper::toVO)
                .toList();
    }

    @Override
    public List<ResenaVO> obtenerTodas() {
        return resenaRepository.findAllByOrderByFechaDesc().stream()
                .map(ResenaMapper::toVO)
                .toList();
    }


    @Override
    public ResenaVO guardar(ResenaVO vo) {
        Resena resena = ResenaMapper.toEntity(vo);
        Libro libro = libroRepository.findById(vo.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        resena.setLibro(libro);
        resena.setFecha(LocalDateTime.now());
        Resena guardada = resenaRepository.save(resena);
        return ResenaMapper.toVO(guardada);
    }
}
