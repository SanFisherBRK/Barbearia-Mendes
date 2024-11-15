package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import BD.Conexao;
import Entities.Clientes;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UpdateCliente {

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
    private Button btn_UpdateCliente;
	@FXML
    private Button btn_Limpar;
	@FXML
    private Clientes cliente;

		@FXML
	    public void preencherDadosCliente(Clientes cliente) {
	        this.cliente = cliente;
	        txt_nome.setText(cliente.getNome());
	        txt_email.setText(cliente.getEmail());
	        txt_telefone.setText(cliente.getTelefone());
	        txt_endereco.setText(cliente.getEndereco());
	        txt_cpf.setText(cliente.getCpf());
	    }

	    public void updateClientes() throws SQLException {
	        cliente.setNome(txt_nome.getText());
	        cliente.setEmail(txt_email.getText());
	        cliente.setTelefone(txt_telefone.getText());
	        cliente.setEndereco(txt_endereco.getText());
	        cliente.setCpf(txt_cpf.getText());

	        String sql = "UPDATE cliente SET nome = ?, email = ?, telefone = ?, endereco = ?, cpf = ? WHERE idcliente = ?";

	        try (Connection conn = new Conexao().getConnection();
	             PreparedStatement enviaParaOBanco = conn.prepareStatement(sql)) {

	            if (cliente.getNome().isEmpty() || cliente.getEmail().isEmpty() || cliente.getTelefone().isEmpty()
	                    || cliente.getEndereco().isEmpty() || cliente.getCpf().isEmpty()) {

	                Alerts.showAlert("Atualizar Cliente", "Preencha todos os campos", "Tentar de novo", AlertType.INFORMATION);

	            } else {
	                enviaParaOBanco.setString(1, cliente.getNome());
	                enviaParaOBanco.setString(2, cliente.getEmail());
	                enviaParaOBanco.setString(3, cliente.getTelefone());
	                enviaParaOBanco.setString(4, cliente.getEndereco());
	                enviaParaOBanco.setString(5, cliente.getCpf());
	                enviaParaOBanco.setInt(6, cliente.getCliente_id());

	                enviaParaOBanco.executeUpdate();

	                Alerts.showAlert("Atualizar Cliente", "Dados atualizados com sucesso", "ok", AlertType.INFORMATION);
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
