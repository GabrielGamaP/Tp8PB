package com.example.ControleDeGastosPB.service;

import com.example.ControleDeGastosPB.modelo.Classificacao;
import com.example.ControleDeGastosPB.repository.ClassificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassificacaoService {

    @Autowired
    private ClassificacaoRepository classificacaoRepository;

    public List<Classificacao> listarClassificacoes() {
        return classificacaoRepository.findAll();
    }

    public Classificacao adicionarClassificacao(Classificacao classificacao) {
        return classificacaoRepository.save(classificacao);
    }


    public Optional<Classificacao> buscarClassificacaoPorId(Long classificacaoId) {
        return classificacaoRepository.findById(classificacaoId);
    }

    public Classificacao buscarClassificacaoPorNome(String nome) {
        return classificacaoRepository.findByNome(nome);
    }

}
