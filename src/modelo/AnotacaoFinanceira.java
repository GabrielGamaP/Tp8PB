package modelo;

import java.time.LocalDate;

public class AnotacaoFinanceira {
    private Long id;
    private LocalDate data;
    private String texto;


    public AnotacaoFinanceira(LocalDate data, String texto) {
        this.data = data;
        this.texto = texto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }


    public String getTexto() {
        return texto;
    }


}
