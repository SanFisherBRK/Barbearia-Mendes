package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BD.Conexao;
import Entities.Usuarios;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UsuarioController {

	// Criar uma variável do tipo Cliente
	Usuarios usuario = new Usuarios();

	// Cria as variáveis do tipo TextField que é os campos onde você digita as
	// informações
	@FXML
	private TextField txt_nome;

	@FXML
	private TextField txt_email;

	@FXML
	private TextField txt_login;

	@FXML
	private TextField txt_senha;

	@FXML
	private Button btn_Cadastrar;

	@FXML
	private Button btn_limparCampos;

	// Faz com que as variáveis da classe Cliente receba nelas o que foi digitado
	// nos campos do formulario.

	public void cadastrarUsuarios() throws SQLException {

		usuario.setNome(txt_nome.getText());
		usuario.setEmail(txt_email.getText());
		usuario.setLogin(txt_login.getText());
		usuario.setSenha(txt_senha.getText());

		// Cria a query de inserção dos dados na tabela do banco de dados.
		String sqlCheckCadastro = "SELECT COUNT(*) FROM usuario WHERE login = ?";
		String sql = "INSERT INTO usuario (nome, email, login, senha) VALUES(?, ?, ?, ?)";

		// Cria a conexão com o banco de dados dentro de um tratamento de erro
		try (Connection conn = new Conexao().getConnection();
				PreparedStatement checarCadastro = conn.prepareStatement(sqlCheckCadastro);
				PreparedStatement enviaParaOBanco = conn.prepareStatement(sql)) {

			checarCadastro.setString(1, usuario.getLogin());
			ResultSet result = checarCadastro.executeQuery();
			result.next();

			// Faz a verificação para ver se todos os campos foram preenchidos
			if (usuario.getNome().isEmpty() || usuario.getEmail().isEmpty() || usuario.getLogin().isEmpty()
					|| usuario.getSenha().isEmpty()) {

				Alerts.showAlert("Cadastrar Usuário", "Preencha todos os campos", "Tentar de novo",
						AlertType.INFORMATION);

			} else {

				int count = result.getInt(1);

				if (count > 0) {

					Alerts.showAlert("Cadastrar Usuário", "Cadastro já existe", "ok", AlertType.INFORMATION);

				} else {

					// Envia os dados digitados para a tabela cliente no banco de dados barbearia
					enviaParaOBanco.setString(1, usuario.getNome());
					enviaParaOBanco.setString(2, usuario.getEmail());
					enviaParaOBanco.setString(3, usuario.getLogin());
					enviaParaOBanco.setString(4, usuario.getSenha());

					enviaParaOBanco.executeUpdate();

					Alerts.showAlert("Cadastrar Usuário", "Cadastro realizado com sucesso", "ok",
							AlertType.INFORMATION);
				}
			}

			// Tratamento de Exceções
		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.getMessage();

		}

	}

	@FXML
	public void limparCampos() {
		txt_nome.clear();
		// txt_Preco.
	}

}
