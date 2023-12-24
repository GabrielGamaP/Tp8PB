package com.example.ControleDeGastosPB.repository;

import com.example.ControleDeGastosPB.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findByNome(String nome);
}
