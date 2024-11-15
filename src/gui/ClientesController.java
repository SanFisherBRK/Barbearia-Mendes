package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BD.Conexao;
import Entities.Clientes;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientesController {

	// Criar uma variável do tipo Cliente
	Clientes cliente = new Clientes();

	// Cria as variáveis do tipo TextField que é os campos onde você digita as
	// informações
	@FXML
	private TextField txt_nome;

	@FXML
	private TextField txt_email;

	@FXML
	private TextField txt_telefone;

	@FXML
	private TextField txt_endereco;

	@FXML
	private TextField txt_cpf;

	@FXML
	private Button btn_Cadastrar;

	@FXML
	private Button btn_limparCampos;

	// Faz com que as variáveis da classe Cliente receba nelas o que foi digitado
	// nos campos do formulario.
	public void cadastrarClientes() throws SQLException {
		cliente.setNome(txt_nome.getText());
		cliente.setEmail(txt_email.getText());
		cliente.setTelefone(txt_telefone.getText());
		cliente.setEndereco(txt_endereco.getText());
		cliente.setCpf(txt_cpf.getText());

		// Cria a query de inserção dos dados na tabela do banco de dados.
		String sqlCheckCadastro = "SELECT COUNT(*) FROM cliente WHERE cpf = ?";
		String sql = "INSERT INTO cliente (nome, email, telefone, endereco, cpf) VALUES(?, ?, ?, ?, ?)";

		// Cria a conexão com o banco de dados dentro de um tratamento de erro
		try (Connection conn = new Conexao().getConnection();
				PreparedStatement checarCadastro = conn.prepareStatement(sqlCheckCadastro);
				PreparedStatement enviaParaOBanco = conn.prepareStatement(sql)) {

			checarCadastro.setString(1, cliente.getCpf());
			ResultSet result = checarCadastro.executeQuery();
			result.next();

			// Faz a verificação para ver se todos os campos foram preenchidos
			if (cliente.getNome().isEmpty() || cliente.getEmail().isEmpty() || cliente.getTelefone().isEmpty()
					|| cliente.getEndereco().isEmpty() || cliente.getCpf().isEmpty()) {

				Alerts.showAlert("Cadastrar Cliente", "Preencha todos os campos", "Tentar de novo",
						AlertType.INFORMATION);

			} else {

				int count = result.getInt(1);

				if (count > 0) {

					Alerts.showAlert("Cadastrar Cliente", "Cadastro já existe", "ok", AlertType.INFORMATION);

				} else {

					// Envia os dados digitados para a tabela cliente no banco de dados barbearia
					enviaParaOBanco.setString(1, cliente.getNome());
					enviaParaOBanco.setString(2, cliente.getEmail());
					enviaParaOBanco.setString(3, cliente.getTelefone());
					enviaParaOBanco.setString(4, cliente.getEndereco());
					enviaParaOBanco.setString(5, cliente.getCpf());

					enviaParaOBanco.executeUpdate();

					Alerts.showAlert("Cadastrar Cliente", "Cadastro realizado com sucesso", "ok",
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
		txt_email.clear();
		txt_telefone.clear();
		txt_endereco.clear();
		txt_cpf.clear();
	}
}
