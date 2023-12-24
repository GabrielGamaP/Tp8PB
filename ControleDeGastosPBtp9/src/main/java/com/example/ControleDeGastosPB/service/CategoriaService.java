package com.example.ControleDeGastosPB.service;

import com.example.ControleDeGastosPB.modelo.Categoria;
import com.example.ControleDeGastosPB.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria adicionarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void excluirCategoria(Long categoriaId) {
        categoriaRepository.deleteById(categoriaId);
    }

    public Optional<Categoria> buscarCategoriaPorId(Long categoriaId) {
        return categoriaRepository.findById(categoriaId);
    }

    public Categoria editarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria buscarCategoriaPorNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }

}
