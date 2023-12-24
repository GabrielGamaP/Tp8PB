package com.example.ControleDeGastosPB.loader;

import com.example.ControleDeGastosPB.modelo.Categoria;
import com.example.ControleDeGastosPB.modelo.Classificacao;
import com.example.ControleDeGastosPB.modelo.Endereco;
import com.example.ControleDeGastosPB.service.CategoriaService;
import com.example.ControleDeGastosPB.service.ClassificacaoService;
import com.example.ControleDeGastosPB.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Como os Maps do tp anterior foram desconsiderados, achei mais pratico fazer a carga inicial em um Loader só e apenas
//com a categoria e classificação, pois como fiz um menu no terminal, achei que se encaixaria melhor com a aplicação.


//Se for preciso ver os Loaders antigos e os Maps peço que veja o Feature correspondente no git do PDF

@Component
public class DataLoader {

    private final ClassificacaoService classificacaoService;
    private final CategoriaService categoriaService;

    @Autowired
    public DataLoader(
            ClassificacaoService classificacaoService,
            CategoriaService categoriaService,
            EnderecoService enderecoService) {
        this.classificacaoService = classificacaoService;
        this.categoriaService = categoriaService;
    }

    public void carregarDadosIniciais() {
        carregarClassificacoes();
        carregarCategorias();
    }

    private void carregarClassificacoes() {
        Classificacao receita = classificacaoService.buscarClassificacaoPorNome("Receita");
        Classificacao despesa = classificacaoService.buscarClassificacaoPorNome("Despesa");

        if (receita == null) {
            Classificacao novaReceita = new Classificacao();
            novaReceita.setNome("Receita");
            classificacaoService.adicionarClassificacao(novaReceita);
        }

        if (despesa == null) {
            Classificacao novaDespesa = new Classificacao();
            novaDespesa.setNome("Despesa");
            classificacaoService.adicionarClassificacao(novaDespesa);
        }
    }

    private void carregarCategorias() {
        Categoria lazer = categoriaService.buscarCategoriaPorNome("Lazer");
        Categoria contas = categoriaService.buscarCategoriaPorNome("Contas");

        if (lazer == null) {
            Categoria nlazer = new Categoria();
            nlazer.setNome("Lazer");
            categoriaService.adicionarCategoria(nlazer);
        }

        if (contas == null) {
            Categoria ncontas = new Categoria();
            ncontas.setNome("Contas");
            categoriaService.adicionarCategoria(ncontas);
        }
    }
}