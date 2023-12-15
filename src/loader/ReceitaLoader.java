package loader;

import modelo.Categoria;
import modelo.Receita;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReceitaLoader {
    private final String FILE_PATH = "resources/receitas.txt";

    public List<Receita> loadReceitas(List<Categoria> categorias) {
        List<Receita> receitas = new ArrayList<>();
        long idCounter = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                LocalDate data = LocalDate.parse(parts[0]);
                String descricao = parts[1];
                double valor = Double.parseDouble(parts[2]);


                Categoria categoria = getCategoriaByName(categorias, parts[3]);

                Receita receita = new Receita(data, descricao, valor, categoria);
                receita.setId(idCounter++);
                receitas.add(receita);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receitas;
    }

    private Categoria getCategoriaByName(List<Categoria> categorias, String nome) {
        for (Categoria categoria : categorias) {
            if (categoria.getNome().equals(nome)) {
                return categoria;
            }
        }
        return null;
    }
}
