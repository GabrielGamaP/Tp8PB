package com.example.ControleDeGastosPB.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "classificacao_id")
    private Classificacao classificacao;

    private String nome;

    private String anotacao;

    private BigDecimal valor;

    private LocalDate data;


    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }


    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }


    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "id=" + id +
                ", categoria=" + categoria +
                ", classificacao=" + classificacao +
                ", nome='" + nome + '\'' +
                ", anotacao='" + anotacao + '\'' +
                ", valor=" + valor +
                ", data=" + data +
                '}';
    }
}
