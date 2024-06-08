package fiap.tds.Entities;

import java.util.Date;

public class Filme {
    private int filmeId;
    private String nomeFilme;
    private String descricaoFilme;
    private String tipoFilme;
    private Date lancamento;

    // Construtor
    public Filme() {
        // Inicializa os atributos
        this.filmeId = 0;
        this.nomeFilme = "";
        this.descricaoFilme = "";
        this.tipoFilme = "";
        this.lancamento = new Date(); // ou null dependendo da lógica da aplicação
    }

    // Getters e Setters
    public int getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(int filmeId) {
        this.filmeId = filmeId;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }

    public String getDescricaoFilme() {
        return descricaoFilme;
    }

    public void setDescricaoFilme(String descricaoFilme) {
        this.descricaoFilme = descricaoFilme;
    }

    public String getTipoFilme() {
        return tipoFilme;
    }

    public void setTipoFilme(String tipoFilme) {
        this.tipoFilme = tipoFilme;
    }

    public Date getLancamento() {
        return lancamento;
    }

    public void setLancamento(Date lancamento) {
        this.lancamento = lancamento;
    }

    // Método toString para imprimir os detalhes do filme
    @Override
    public String toString() {
        return "Filme{" +
                "filmeId=" + filmeId +
                ", nomeFilme='" + nomeFilme + '\'' +
                ", descricaoFilme='" + descricaoFilme + '\'' +
                ", tipoFilme='" + tipoFilme + '\'' +
                ", lancamento=" + lancamento +
                '}';
    }
}
