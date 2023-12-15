package service;

import modelo.Despesa;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DespesaService {
    private final Map<Long, Despesa> despesas;

    public DespesaService(List<Despesa> despesas) {
        this.despesas = new HashMap<>();
        for (Despesa despesa : despesas) {
            this.despesas.put(despesa.getId(), despesa);
        }
    }

    public void incluirDespesa(Despesa despesa) {
        long novoId = despesas.size() + 1;
        despesa.setId(novoId);
        despesas.put(novoId, despesa);
    }

    public List<Despesa> obterListaDespesas() {
        return new ArrayList<>(despesas.values());
    }
}
