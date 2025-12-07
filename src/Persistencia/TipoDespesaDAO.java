package Persistencia;

import Class.TipoDespesa;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.io.IOException;

public class TipoDespesaDAO {

    private final Path arquivo;

    public TipoDespesaDAO(Path dataDir) {
        this.arquivo = dataDir.resolve("tipos.txt");
        try {
            Files.createDirectories(dataDir);
            if (!Files.exists(arquivo)) {
                Files.createFile(arquivo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized List<TipoDespesa> listar() {
        try {
            return Files.lines(arquivo).filter(l -> !l.trim().isEmpty()).map(TipoDespesa::fromCsv).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public synchronized void salvar(TipoDespesa t) throws IOException {
        List<TipoDespesa> todos = listar();
        if (t.getId() == 0) {
            int next = todos.stream().mapToInt(TipoDespesa::getId).max().orElse(0) + 1;
            t.setId(next);
            todos.add(t);
        } else {
            todos.removeIf(x -> x.getId() == t.getId());
            todos.add(t);
        }
        List<String> lines = todos.stream().map(TipoDespesa::toCsv).collect(Collectors.toList());
        Files.write(arquivo, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    public synchronized void remover(int id) throws IOException {
        List<TipoDespesa> todos = listar();
        todos.removeIf(x -> x.getId() == id);
        List<String> lines = todos.stream().map(TipoDespesa::toCsv).collect(Collectors.toList());
        Files.write(arquivo, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }
}
