package loader;

import modelo.Categoria;
import modelo.Despesa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DespesaLoader {
    private final String FILE_PATH = "resources/despesas.txt";

    public List<Despesa> loadDespesas(List<Categoria> categorias) {
        List<Despesa> despesas = new ArrayList<>();
        long idCounter = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                LocalDate data = LocalDate.parse(parts[0]);
                String descricao = parts[1];
                double valor = Double.parseDouble(parts[2]);
                boolean concluida = Boolean.parseBoolean(parts[3]);


                Categoria categoria = getCategoriaByName(categorias, parts[4]);

                Despesa despesa = new Despesa(data, descricao, valor, concluida, categoria);
                despesa.setId(idCounter++);
                despesas.add(despesa);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return despesas;
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
