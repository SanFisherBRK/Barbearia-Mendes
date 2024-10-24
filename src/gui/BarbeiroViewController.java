package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BD.Conexao;
import Entities.Barbeiros;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class BarbeiroViewController {
	// Criar uma variável do tipo Barbeiros
	Barbeiros barbeiro = new Barbeiros();

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
	public void cadastrarBarbeiros() throws SQLException {
		barbeiro.setNome(txt_nome.getText());
		barbeiro.setEmail(txt_email.getText());
		barbeiro.setTelefone(txt_telefone.getText());
		barbeiro.setEndereco(txt_endereco.getText());
		barbeiro.setCpf(txt_cpf.getText());

		// Cria a query de inserção dos dados na tabela do banco de dados.
		String sqlCheckCadastro = "SELECT COUNT(*) FROM barbeiro WHERE cpf = ?";
		String sql = "INSERT INTO barbeiro (nome, email, telefone, endereco, cpf) VALUES(?, ?, ?, ?, ?)";

		// Cria a conexão com o banco de dados dentro de um tratamento de erro
		try (Connection conn = new Conexao().getConnection();
				PreparedStatement checarCadastro = conn.prepareStatement(sqlCheckCadastro);
				PreparedStatement enviaParaOBanco = conn.prepareStatement(sql)) {

			checarCadastro.setString(1, barbeiro.getCpf());
			ResultSet result = checarCadastro.executeQuery();
			result.next();

			// Faz a verificação para ver se todos os campos foram preenchidos
			if (barbeiro.getNome().isEmpty() || barbeiro.getEmail().isEmpty() || barbeiro.getTelefone().isEmpty()
					|| barbeiro.getEndereco().isEmpty() || barbeiro.getCpf().isEmpty()) {

				Alerts.showAlert("Cadastrar Cliente", "Preencha todos os campos", "Tentar de novo",
						AlertType.INFORMATION);

			} else {

				int count = result.getInt(1);

				if (count > 0) {

					Alerts.showAlert("Cadastrar Barbeiro", "Cadastro já existe", "ok", AlertType.INFORMATION);

				} else {

					// Envia os dados digitados para a tabela cliente no banco de dados barbearia
					enviaParaOBanco.setString(1, barbeiro.getNome());
					enviaParaOBanco.setString(2, barbeiro.getEmail());
					enviaParaOBanco.setString(3, barbeiro.getTelefone());
					enviaParaOBanco.setString(4, barbeiro.getEndereco());
					enviaParaOBanco.setString(5, barbeiro.getCpf());

					enviaParaOBanco.executeUpdate();

					Alerts.showAlert("Cadastrar Cliente", "Cadastro realizado com sucesso", "ok",
							AlertType.INFORMATION);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
