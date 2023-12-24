package com.example.ControleDeGastosPB.controller;


import com.example.ControleDeGastosPB.modelo.Categoria;
import com.example.ControleDeGastosPB.modelo.Resultado;
import com.example.ControleDeGastosPB.service.CategoriaService;
import com.example.ControleDeGastosPB.service.ResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ResultadoService resultadoService;

    @GetMapping("/{categoriaId}/resultados")
    public List<Resultado> getResultadosPorCategoria(@PathVariable Long categoriaId) {
        Categoria categoria = categoriaService.buscarCategoriaPorId(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        return resultadoService.getResultadosPorCategoria(categoria);
    }

    @PostMapping("/incluir")
    public Categoria incluirCategoria(@RequestBody Categoria categoria) {
        return categoriaService.adicionarCategoria(categoria);
    }

    @DeleteMapping("/excluir/{categoriaId}")
    public Resultado excluirCategoria(@PathVariable Long categoriaId) {
        categoriaService.excluirCategoria(categoriaId);
        return new Resultado();
    }
}
