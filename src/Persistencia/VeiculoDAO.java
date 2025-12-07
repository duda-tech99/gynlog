package Persistencia;

import Class.Veiculo;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.io.IOException;

public class VeiculoDAO {

    private final Path arquivo;

    public VeiculoDAO(Path dataDir) {
        this.arquivo = dataDir.resolve("veiculos.txt");
        try {
            Files.createDirectories(dataDir);
            if (!Files.exists(arquivo)) {
                Files.createFile(arquivo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized List<Veiculo> listar() {
        try {
            return Files.lines(arquivo).filter(l -> !l.trim().isEmpty()).map(Veiculo::fromCsv).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Salva (insere ou atualiza) veículo no arquivo. Gera id sequencial se
     * id==0.
     */
    public synchronized void salvar(Veiculo v) throws IOException {
        List<Veiculo> todos = listar();
        if (v.getId() == 0) {
            int next = todos.stream().mapToInt(Veiculo::getId).max().orElse(0) + 1;
            v.setId(next);
            todos.add(v);
        } else {
            todos.removeIf(x -> x.getId() == v.getId());
            todos.add(v);
        }
        List<String> lines = todos.stream().map(Veiculo::toCsv).collect(Collectors.toList());
        Files.write(arquivo, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    public synchronized void remover(int id) throws IOException {
        List<Veiculo> todos = listar();
        todos.removeIf(x -> x.getId() == id);
        List<String> lines = todos.stream().map(Veiculo::toCsv).collect(Collectors.toList());
        Files.write(arquivo, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    public Optional<Veiculo> buscarPorId(int id) {
        return listar().stream().filter(v -> v.getId() == id).findFirst();
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return listar().stream().filter(v -> placa.equalsIgnoreCase(v.getPlaca())).findFirst();
    }
}
