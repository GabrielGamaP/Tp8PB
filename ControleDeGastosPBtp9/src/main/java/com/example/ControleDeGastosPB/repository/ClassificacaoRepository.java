package com.example.ControleDeGastosPB.repository;

import com.example.ControleDeGastosPB.modelo.Classificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long> {

    Classificacao findByNome(String nome);

}
