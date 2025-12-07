package Class;

public class Veiculo {

    private int id;
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private boolean ativo;

    public Veiculo() {
    }

    public Veiculo(int id, String placa, String marca, String modelo, int ano, boolean ativo) {
        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String toCsv() {
        return String.format("%d;%s;%s;%s;%d;%b", id, placa, marca, modelo, ano, ativo);
    }

    public static Veiculo fromCsv(String line) {
        String[] p = line.split(";");
        if (p.length < 6) {
            return null;
        }
        try {
            return new Veiculo(Integer.parseInt(p[0]), p[1], p[2], p[3], Integer.parseInt(p[4]), Boolean.parseBoolean(p[5]));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return placa + " - " + marca + " " + modelo + " (" + ano + ")" + (ativo ? "" : " [INATIVO]");
    }
}
