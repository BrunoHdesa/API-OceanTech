package fiap.tds.Repository;

import fiap.tds.Entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "rm553799";
    private static final String PASSWORD = "fiap24";

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM USUARIO");
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setNome(rs.getString("NOME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setSenha(rs.getString("SENHA"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public User getUser(int id) {
        User user = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM USUARIO WHERE ID = ?");
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("ID"));
                user.setNome(rs.getString("USUARIO"));
                user.setEmail(rs.getString("EMAIL"));
                user.setSenha(rs.getString("SENHA"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void createUser(User user) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stat = conn.prepareStatement("INSERT INTO USUARIO(USUARIO, EMAIL, SENHA) VALUES (?,?,?)");
            stat.setString(1, user.getNome());
            stat.setString(2, user.getEmail());
            stat.setString(3, user.getSenha());
            stat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String sql = "UPDATE USUARIO SET USUARIO = ?, EMAIL = ?, SENHA = ? WHERE ID = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, user.getNome());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getSenha());
                statement.setInt(4, user.getId());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new RuntimeException("User não encontrado");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String sql = "DELETE FROM USUARIO WHERE ID = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setInt(1, id);

                int delete = statement.executeUpdate();
                if (delete == 0) {
                    throw new RuntimeException("User não encontrado");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User authenticateUser(String nome, String senha) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT * FROM USUARIO WHERE USUARIO = ? AND SENHA = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, senha);
            resultSet = statement.executeQuery();

            // Verificar se o usuário foi encontrado
            if (resultSet.next()) {
                // Se encontrado, criar um objeto User com os dados do resultado
                int id = resultSet.getInt("id");
                String userEmail = resultSet.getString("email");
                user = new User(id, userEmail, nome, senha); // Substitua pelos campos reais do seu banco de dados
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }
}
