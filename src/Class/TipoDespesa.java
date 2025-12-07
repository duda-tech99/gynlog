package Class;

public class TipoDespesa {

    private int id;
    private String descricao;

    public TipoDespesa() {
    }

    public TipoDespesa(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String toCsv() {
        return String.format("%d;%s", id, descricao);
    }

    public static TipoDespesa fromCsv(String line) {
        String[] p = line.split(";");
        if (p.length < 2) {
            return null;
        }
        try {
            return new TipoDespesa(Integer.parseInt(p[0]), p[1]);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return descricao;
    }
}
