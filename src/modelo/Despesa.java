package modelo;

import java.time.LocalDate;

public class Despesa {
    private Long id;

    private String descricao;
    private double valor;


    public Despesa(LocalDate data, String descricao, double valor, boolean concluida, Categoria categoria) {

        this.descricao = descricao;
        this.valor = valor;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDescricao() {
        return descricao;
    }


    public double getValor() {
        return valor;
    }


}
