package Persistencia;

import Class.movimentacao;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.io.IOException;
import java.time.LocalDate;

public class movimentacaoDAO {

    private final Path arquivo;

    public movimentacaoDAO(Path dataDir) {
        this.arquivo = dataDir.resolve("movimentacoes.txt");
        try {
            Files.createDirectories(dataDir);
            if (!Files.exists(arquivo)) {
                Files.createFile(arquivo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized List<movimentacao> listar() {
        try {
            return Files.lines(arquivo).filter(l -> !l.trim().isEmpty()).map(movimentacao::fromCsv).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public synchronized void salvar(movimentacao m) throws IOException {
        List<movimentacao> todos = listar();
        if (m.getId() == 0) {
            int next = todos.stream().mapToInt(movimentacao::getId).max().orElse(0) + 1;
            m.setId(next);
            todos.add(m);
        } else {
            todos.removeIf(x -> x.getId() == m.getId());
            todos.add(m);
        }
        List<String> lines = todos.stream().map(movimentacao::toCsv).collect(Collectors.toList());
        Files.write(arquivo, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    public synchronized List<movimentacao> listarPorVeiculo(int idVeiculo) {
        return listar().stream().filter(m -> m.getIdVeiculo() == idVeiculo).collect(Collectors.toList());
    }

    public synchronized List<movimentacao> listarPorMesAno(int mes, int ano) {
        return listar().stream().filter(m -> m.getData().getMonthValue() == mes && m.getData().getYear() == ano).collect(Collectors.toList());
    }
}
