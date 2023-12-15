package service;

import modelo.AnotacaoFinanceira;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnotacaoFinanceiraService {
    private final Map<Long, AnotacaoFinanceira> anotacoes;

    public AnotacaoFinanceiraService(List<AnotacaoFinanceira> anotacoes) {
        this.anotacoes = new HashMap<>();
        for (AnotacaoFinanceira anotacao : anotacoes) {
            this.anotacoes.put(anotacao.getId(), anotacao);
        }
    }

    public void incluirAnotacao(AnotacaoFinanceira anotacao) {
        long novoId = anotacoes.size() + 1;
        anotacao.setId(novoId);
        anotacoes.put(novoId, anotacao);
    }

    public List<AnotacaoFinanceira> obterListaAnotacoes() {
        return new ArrayList<>(anotacoes.values());
    }
}
