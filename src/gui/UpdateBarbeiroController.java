package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import BD.Conexao;
import Entities.Barbeiros;
import Entities.Clientes;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UpdateBarbeiroController {

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
    private Button btn_Salvar;
	@FXML
    private Button btn_limparCampos;
	@FXML
    private Barbeiros barbeiro;

		@FXML
	    public void preencherDadosBarbeiro(Barbeiros barbeiro) {
	        this.barbeiro = barbeiro;
	        txt_nome.setText(barbeiro.getNome());
	        txt_email.setText(barbeiro.getEmail());
	        txt_telefone.setText(barbeiro.getTelefone());
	        txt_endereco.setText(barbeiro.getEndereco());
	        txt_cpf.setText(barbeiro.getCpf());
	    }

	    public void updateBarbeiros() throws SQLException {
	    	barbeiro.setNome(txt_nome.getText());
	    	barbeiro.setEmail(txt_email.getText());
	    	barbeiro.setTelefone(txt_telefone.getText());
	    	barbeiro.setEndereco(txt_endereco.getText());
	    	barbeiro.setCpf(txt_cpf.getText());

	        String sql = "UPDATE barbeiro SET nome = ?, email = ?, telefone = ?, endereco = ?, cpf = ? WHERE idbarbeiro = ?";

	        try (Connection conn = new Conexao().getConnection();
	             PreparedStatement enviaParaOBanco = conn.prepareStatement(sql)) {

	            if (barbeiro.getNome().isEmpty() || barbeiro.getEmail().isEmpty() || barbeiro.getTelefone().isEmpty()
	                    || barbeiro.getEndereco().isEmpty() || barbeiro.getCpf().isEmpty()) {

	                Alerts.showAlert("Atualizar Barbeiro", "Preencha todos os campos", "Tentar de novo", AlertType.INFORMATION);

	            } else {
	                enviaParaOBanco.setString(1, barbeiro.getNome());
	                enviaParaOBanco.setString(2, barbeiro.getEmail());
	                enviaParaOBanco.setString(3, barbeiro.getTelefone());
	                enviaParaOBanco.setString(4, barbeiro.getEndereco());
	                enviaParaOBanco.setString(5, barbeiro.getCpf());
	                enviaParaOBanco.setInt(6, barbeiro.getIdbarbeiro());

	                enviaParaOBanco.executeUpdate();

	                Alerts.showAlert("Atualizar Barbeiro", "Dados atualizados com sucesso", "ok", AlertType.INFORMATION);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.getMessage();
	        }
	        
	        
	    }
	    public void limparCampos() {
	        txt_nome.clear();
	        txt_email.clear();
	        txt_telefone.clear();
	        txt_endereco.clear();
	        txt_cpf.clear();
	    }
	}
