package fiap.tds.Entities;

public class Feedback {
    private int id;
    private String nome;
    private String email;
    private String avaliacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Feedback() {
    }

    public Feedback(int id, String nome, String email, String avaliacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", avaliacao='" + avaliacao + '\'' +
                '}';
    }
}
