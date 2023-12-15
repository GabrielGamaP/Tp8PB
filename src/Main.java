import loader.AnotacaoFinanceiraLoader;
import loader.CategoriaLoader;
import loader.DespesaLoader;
import loader.ReceitaLoader;
import modelo.AnotacaoFinanceira;
import modelo.Categoria;
import modelo.Despesa;
import modelo.Receita;
import service.AnotacaoFinanceiraService;
import service.CategoriaService;
import service.DespesaService;
import service.ReceitaService;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        CategoriaLoader categoriaLoader = new CategoriaLoader();
        List<Categoria> categorias = categoriaLoader.loadCategorias();


        AnotacaoFinanceiraLoader financeiraLoader = new AnotacaoFinanceiraLoader();
        List<AnotacaoFinanceira> financeiras = financeiraLoader.loadAnotacoes();


        DespesaLoader despesaLoader = new DespesaLoader();
        List<Despesa> despesas = despesaLoader.loadDespesas(categorias);


        ReceitaLoader receitaLoader = new ReceitaLoader();
        List<Receita> receitas = receitaLoader.loadReceitas(categorias);


        CategoriaService categoriaService = new CategoriaService(categorias);
        AnotacaoFinanceiraService financeiraService = new AnotacaoFinanceiraService(financeiras);
        DespesaService despesaService = new DespesaService(despesas);
        ReceitaService receitaService = new ReceitaService(receitas);



        Categoria novaCategoria = new Categoria("Testeinclusão", "Testeinclusão");
        categoriaService.incluirCategoria(novaCategoria);

        AnotacaoFinanceira novaAnotacao = new AnotacaoFinanceira(LocalDate.now(), "Testeinclusão");
        financeiraService.incluirAnotacao(novaAnotacao);

        Despesa novaDespesa = new Despesa(LocalDate.now(), "Testeinclusão", 100.0, false, novaCategoria);
        despesaService.incluirDespesa(novaDespesa);

        Receita novaReceita = new Receita(LocalDate.now(), "Testeinclusão", 200.0, novaCategoria);
        receitaService.incluirReceita(novaReceita);


        List<Categoria> listaCategorias = categoriaService.obterListaCategorias();
        List<AnotacaoFinanceira> listaAnotacoes = financeiraService.obterListaAnotacoes();
        List<Despesa> listaDespesas = despesaService.obterListaDespesas();
        List<Receita> listaReceitas = receitaService.obterListaReceitas();


        System.out.println("Lista de Categorias:");
        for (Categoria categoria : listaCategorias) {
            System.out.println(categoria.getNome() + ": " + categoria.getDescricao());
        }

        System.out.println("\nLista de Anotações:");
        for (AnotacaoFinanceira anotacao : listaAnotacoes) {
            System.out.println(anotacao.getData() + ": " + anotacao.getTexto());
        }

        System.out.println("\nLista de Despesas:");
        for (Despesa despesa : listaDespesas) {
            System.out.println(despesa.getDescricao() + ": " + despesa.getValor());
        }

        System.out.println("\nLista de Receitas:");
        for (Receita receita : listaReceitas) {
            System.out.println(receita.getDescricao() + ": " + receita.getValor());
        }

    }
}
