package loader;

import modelo.Categoria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaLoader {
    private final String FILE_PATH = "resources/categorias.txt";

    public List<Categoria> loadCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        long idCounter = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String nome = parts[0];
                String descricao = parts[1];

                Categoria categoria = new Categoria(nome, descricao);
                categoria.setId(idCounter++);
                categorias.add(categoria);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categorias;
    }
}

