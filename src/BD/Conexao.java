package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	public Connection getConnection() throws SQLException{
		String url  = "jdbc:mysql://localhost:3306/barbearia";
		String user = "root";
		String password = "1234";
		
		return DriverManager.getConnection(url, user, password);
	}

}
