package com.example.ControleDeGastosPB.controller;

import com.example.ControleDeGastosPB.modelo.Classificacao;
import com.example.ControleDeGastosPB.modelo.Resultado;
import com.example.ControleDeGastosPB.service.ClassificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classificacoes")
public class ClassificacaoController {

    @Autowired
    private ClassificacaoService classificacaoService;

    @GetMapping
    public List<Classificacao> listarClassificacoes() {
        return classificacaoService.listarClassificacoes();
    }

    @PostMapping("/incluir")
    public Classificacao adicionarClassificacao(@RequestBody Classificacao classificacao) {
        return classificacaoService.adicionarClassificacao(classificacao);
    }

    @GetMapping("/{classificacaoId}")
    public Classificacao buscarClassificacaoPorId(@PathVariable Long classificacaoId) {
        return classificacaoService.buscarClassificacaoPorId(classificacaoId)
                .orElseThrow(() -> new RuntimeException("Classificacao não encontrada"));
    }

    @GetMapping("/nome/{nome}")
    public Classificacao buscarClassificacaoPorNome(@PathVariable String nome) {
        return classificacaoService.buscarClassificacaoPorNome(nome);
    }


}
