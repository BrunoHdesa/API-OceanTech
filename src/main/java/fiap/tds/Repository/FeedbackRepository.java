package fiap.tds.Repository;

import fiap.tds.Entities.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackRepository {

    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "rm553799";
    private static final String PASSWORD = "fiap24";

    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM FEEDBACK");
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("ID"));
                feedback.setNome(rs.getString("NOME"));
                feedback.setEmail(rs.getString("EMAIL"));
                feedback.setAvaliacao(rs.getString("AVALIACAO"));
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return feedbacks;
    }

    public Feedback getFeedback(int id) {
        Feedback feedback = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM FEEDBACK WHERE ID = ?");
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                feedback = new Feedback();
                feedback.setId(rs.getInt("ID"));
                feedback.setNome(rs.getString("NOME"));
                feedback.setEmail(rs.getString("EMAIL"));
                feedback.setAvaliacao(rs.getString("AVALIACAO"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return feedback;
    }

    public void createFeedback(Feedback feedback) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stat = conn.prepareStatement("INSERT INTO FEEDBACK(NOME, EMAIL, AVALIACAO) VALUES (?,?,?)");
            stat.setString(1, feedback.getNome());
            stat.setString(2, feedback.getEmail());
            stat.setString(3, feedback.getAvaliacao());
            stat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFeedback(Feedback feedback) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String sql = "UPDATE FEEDBACK SET NOME = ?, EMAIL = ?, AVALIACAO = ? WHERE ID = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, feedback.getNome());
                statement.setString(2, feedback.getEmail());
                statement.setString(3, feedback.getAvaliacao());
                statement.setInt(4, feedback.getId());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new RuntimeException("Feedback não encontrado");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFeedback(int id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String sql = "DELETE FROM FEEDBACK WHERE ID = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setInt(1, id);

                int delete = statement.executeUpdate();
                if (delete == 0) {
                    throw new RuntimeException("Feedback não encontrado");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
