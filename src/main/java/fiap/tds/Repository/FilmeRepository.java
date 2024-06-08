package fiap.tds.Repository;

import fiap.tds.Entities.Filme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmeRepository {

    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "rm553799";
    private static final String PASSWORD = "fiap24";

    public List<Filme> getAllFilmes() {
        List<Filme> filmes = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM FILMES");
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Filme filme = new Filme();
                filme.setFilmeId(rs.getInt("FilmeId"));
                filme.setNomeFilme(rs.getString("NomeFilme"));
                filme.setDescricaoFilme(rs.getString("DescriçãoFilme"));
                filme.setTipoFilme(rs.getString("TipoFilme"));
                filme.setLancamento(rs.getDate("Lancamento"));
                filmes.add(filme);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filmes;
    }

    public Filme getFilmes(int id) {
        Filme filme = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM FILMES WHERE FilmeId = ?");
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                filme = new Filme();
                filme.setFilmeId(rs.getInt("FilmeId"));
                filme.setNomeFilme(rs.getString("NomeFilme"));
                filme.setDescricaoFilme(rs.getString("DescriçãoFilme"));
                filme.setTipoFilme(rs.getString("TipoFilme"));
                filme.setLancamento(rs.getDate("Lancamento"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filme;
    }

    public void createFilme(Filme filme) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stat = conn.prepareStatement("INSERT INTO FILMES(NomeFilme, DescriçãoFilme, TipoFilme, Lancamento) VALUES (?,?,?,?)");
            stat.setString(1, filme.getNomeFilme());
            stat.setString(2, filme.getDescricaoFilme());
            stat.setString(3, filme.getTipoFilme());
            stat.setDate(4, new java.sql.Date(filme.getLancamento().getTime()));
            stat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFilme(Filme filme) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String sql = "UPDATE FILMES SET NomeFilme = ?, DescriçãoFilme = ?, TipoFilme = ?, Lancamento = ? WHERE FilmeId = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, filme.getNomeFilme());
                statement.setString(2, filme.getDescricaoFilme());
                statement.setString(3, filme.getTipoFilme());
                statement.setDate(4, new java.sql.Date(filme.getLancamento().getTime()));
                statement.setInt(5, filme.getFilmeId());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new RuntimeException("Filme não encontrado");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFilme(int id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String sql = "DELETE FROM FILMES WHERE FilmeId = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setInt(1, id);

                int delete = statement.executeUpdate();
                if (delete == 0) {
                    throw new RuntimeException("Filme não encontrado");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
