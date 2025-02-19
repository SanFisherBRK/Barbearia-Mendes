package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import BD.Conexao;
import Entities.Clientes;
import Entities.Usuarios;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UpdateUsuarioController {

	@FXML
    private TextField txt_nome;
	@FXML
    private TextField txt_email;
	@FXML
    private TextField txt_login;
	@FXML
    private TextField txt_senha;
	@FXML
    private Button btn_Salvar;
	@FXML
    private Button btn_limparCampos;
	@FXML
    private Usuarios usuario;

		@FXML
	    public void preencherDadosUsuarios(Usuarios usuario) {
	        this.usuario = usuario;
	        txt_nome.setText(usuario.getNome());
	        txt_email.setText(usuario.getEmail());
	        txt_login.setText(usuario.getLogin());
	        txt_senha.setText(usuario.getSenha());
	        
	    }

	    public void updateUsuarios() throws SQLException {
	    	
	    	usuario.setNome(txt_nome.getText());
	    	usuario.setEmail(txt_email.getText());
	    	usuario.setLogin(txt_login.getText());
	    	usuario.setSenha(txt_senha.getText());
	    

	        String sql = "UPDATE usuario SET nome = ?, email = ?, login = ?, senha = ? WHERE idusuario = ?";

	        try (Connection conn = new Conexao().getConnection();
	             PreparedStatement enviaParaOBanco = conn.prepareStatement(sql)) {

	            if (usuario.getNome().isEmpty() || usuario.getEmail().isEmpty() || usuario.getLogin().isEmpty()
	                    || usuario.getSenha().isEmpty()){

	                Alerts.showAlert("Atualizar usuário", "Preencha todos os campos", "Tentar de novo", AlertType.INFORMATION);

	            } else {
	                enviaParaOBanco.setString(1, usuario.getNome());
	                enviaParaOBanco.setString(2, usuario.getEmail());
	                enviaParaOBanco.setString(3, usuario.getLogin());
	                enviaParaOBanco.setString(4, usuario.getSenha());
	                enviaParaOBanco.setInt(5, usuario.getId());

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
	        txt_login.clear();
	        txt_senha.clear();
	        
	    }
	}
