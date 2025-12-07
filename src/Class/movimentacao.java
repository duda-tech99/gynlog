package Class;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Movimentacao {

    private int id;
    private int idVeiculo;
    private int idTipo;
    private String descricao;
    private LocalDate data;
    private BigDecimal valor;

    public Movimentacao() {
    }

    public Movimentacao(int id, int idVeiculo, int idTipo, String descricao, LocalDate data, BigDecimal valor) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.idTipo = idTipo;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String toCsv() {
        return String.format("%d;%d;%d;%s;%s;%s", id, idVeiculo, idTipo, descricao, data.toString(), valor.toPlainString());
    }

    public static Movimentacao fromCsv(String line) {
        String[] p = line.split(";");
        if (p.length < 6) {
            return null;
        }
        try {
            return new Movimentacao(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]), p[3], LocalDate.parse(p[4]), new java.math.BigDecimal(p[5]));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return data + " - " + descricao + " (" + valor + ")";
    }
}
