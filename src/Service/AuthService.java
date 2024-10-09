package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthService {
	public boolean authenticate(String username, String password) {
        boolean isValidUser = false;

        String url = "jdbc:mysql://localhost:3306/barbearia";
        String nome = "root";
        String senha = "1234";

        try (Connection conn = DriverManager.getConnection(url, nome, senha)) {
            String sql = "SELECT * FROM usuario WHERE nome = ? AND senha = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, senha);

            ResultSet resultSet = statement.executeQuery();
            isValidUser = resultSet.next(); // returns true if there's a record

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValidUser;
    
	}

}
