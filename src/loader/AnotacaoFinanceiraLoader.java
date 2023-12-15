package loader;

import modelo.AnotacaoFinanceira;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnotacaoFinanceiraLoader {
    private final String FILE_PATH = "resources/anotacoes.txt";

    public List<AnotacaoFinanceira> loadAnotacoes() {
        List<AnotacaoFinanceira> anotacoes = new ArrayList<>();
        long idCounter = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                LocalDate data = LocalDate.parse(parts[0]);
                String texto = parts[1];

                AnotacaoFinanceira anotacao = new AnotacaoFinanceira(data, texto);
                anotacao.setId(idCounter++);
                anotacoes.add(anotacao);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return anotacoes;
    }
}
