package service;

import modelo.Receita;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceitaService {
    private final Map<Long, Receita> receitas;

    public ReceitaService(List<Receita> receitas) {
        this.receitas = new HashMap<>();
        for (Receita receita : receitas) {
            this.receitas.put(receita.getId(), receita);
        }
    }

    public void incluirReceita(Receita receita) {
        long novoId = receitas.size() + 1;
        receita.setId(novoId);
        receitas.put(novoId, receita);
    }

    public List<Receita> obterListaReceitas() {
        return new ArrayList<>(receitas.values());
    }
}
