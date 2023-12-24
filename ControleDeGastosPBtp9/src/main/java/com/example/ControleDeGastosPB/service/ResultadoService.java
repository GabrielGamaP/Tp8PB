package com.example.ControleDeGastosPB.service;

import com.example.ControleDeGastosPB.modelo.Resultado;
import com.example.ControleDeGastosPB.repository.ResultadoRepository;
import com.example.ControleDeGastosPB.modelo.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;

    public List<Resultado> listarResultados() {
        return resultadoRepository.findAll();
    }

    public Resultado adicionarResultado(Resultado resultado) {
        return resultadoRepository.save(resultado);
    }

    public void excluirResultado(Long resultadoId) {
        resultadoRepository.deleteById(resultadoId);
    }

    public Optional<Resultado> buscarResultadoPorId(Long resultadoId) {
        return resultadoRepository.findById(resultadoId);
    }

    public Resultado editarResultado(Resultado resultado) {
        return resultadoRepository.save(resultado);
    }

    public boolean existeResultadoComCategoria(Categoria categoria) {
        return resultadoRepository.existsByCategoria(categoria);
    }
}
