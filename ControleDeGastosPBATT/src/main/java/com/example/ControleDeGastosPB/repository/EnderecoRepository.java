package com.example.ControleDeGastosPB.repository;

import com.example.ControleDeGastosPB.modelo.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Endereco findByCep(String cep);
}