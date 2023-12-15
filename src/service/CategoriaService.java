package service;

import modelo.Categoria;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriaService {
    private final Map<Long, Categoria> categorias;
    private long contadorId;

    public CategoriaService(List<Categoria> categorias) {
        this.categorias = new HashMap<>();
        this.contadorId = 0;

        for (Categoria categoria : categorias) {
            this.categorias.put(categoria.getId(), categoria);

            if (categoria.getId() > contadorId) {
                contadorId = categoria.getId();
            }
        }
    }

    public void incluirCategoria(Categoria categoria) {
        contadorId++;
        categoria.setId(contadorId);
        categorias.put(contadorId, categoria);
    }


    public List<Categoria> obterListaCategorias() {
        return new ArrayList<>(categorias.values());
    }
}
