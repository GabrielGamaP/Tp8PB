package com.example.ControleDeGastosPB.modelo;

import javax.persistence.*;

@Entity
public class Classificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Classificacao{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
