package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BD.Conexao;
import Entities.Servicos;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ViewServiceController {
	
	Servicos servico = new Servicos();
	
	@FXML
	private TextField txt_Nome;
	
	@FXML
	private TextField txt_Preco;
	
	@FXML
	private TextField txt_Descricao;
	
	@FXML
	private Button btn_Cadastrar;
	
	@FXML
	private Button btn_Limpar;
	
	@FXML
	public void cadastrarServico() throws SQLException {
		
	    // Verifica se algum campo está vazio antes de prosseguir
	    if (txt_Nome.getText().isEmpty() || txt_Preco.getText().isEmpty() || txt_Descricao.getText().isEmpty()) {
	        Alerts.showAlert("Cadastrar Serviço", "Preencha todos os campos", "Tentar de novo", AlertType.INFORMATION);
	        return;
	    }

	    servico.setNome(txt_Nome.getText());
	    servico.setDescricao(txt_Descricao.getText());

	    try {
	        servico.setPreco(Double.parseDouble(txt_Preco.getText()));
	    } catch (NumberFormatException e) {
	        Alerts.showAlert("Cadastrar Serviço", "Preço inválido", "Tentar de novo", AlertType.INFORMATION);
	        return;
	    }

	    // Cria a query de inserção dos dados na tabela do banco de dados.
	    String sqlCheckCadastro = "SELECT COUNT(*) FROM servico WHERE nome = ?";
	    String sql = "INSERT INTO servico (nome, descricao, preco) VALUES(?, ?, ?)";

	    try (Connection conn = new Conexao().getConnection();
	         PreparedStatement checarCadastro = conn.prepareStatement(sqlCheckCadastro);
	         PreparedStatement enviaParaOBanco = conn.prepareStatement(sql)) {

	        checarCadastro.setString(1, servico.getNome());
	        ResultSet result = checarCadastro.executeQuery();
	        result.next();

	        // Faz a verificação para ver se o nome já existe no banco de dados
	        int count = result.getInt(1);
	        
	        if (count > 0) {
	            Alerts.showAlert("Cadastrar Serviço", "Cadastro já existe", "ok", AlertType.INFORMATION);
	        } else {
	            // Envia os dados digitados para a tabela cliente no banco de dados barbearia
	            enviaParaOBanco.setString(1, servico.getNome());
	            enviaParaOBanco.setString(2, servico.getDescricao());
	            enviaParaOBanco.setDouble(3, servico.getPreco());
	            enviaParaOBanco.executeUpdate();

	            Alerts.showAlert("Cadastrar Serviço", "Cadastro realizado com sucesso", "ok", AlertType.INFORMATION);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        Alerts.showAlert("Erro", "Erro ao acessar o banco de dados", e.getMessage(), AlertType.ERROR);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        Alerts.showAlert("Erro", "Erro inesperado", e.getMessage(), AlertType.ERROR);
	    }
	}

}
