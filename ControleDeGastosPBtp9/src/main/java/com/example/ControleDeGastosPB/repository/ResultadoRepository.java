package com.example.ControleDeGastosPB.repository;

import com.example.ControleDeGastosPB.modelo.Categoria;
import com.example.ControleDeGastosPB.modelo.Classificacao;
import com.example.ControleDeGastosPB.modelo.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    boolean existsByCategoria(Categoria categoria);
}
