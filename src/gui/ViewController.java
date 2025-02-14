package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ViewController implements Initializable {

	@FXML
	private Label label;
	
	@FXML
    private MenuItem menuItemAgendarServico;

	@FXML
	private MenuItem menuItemClientes;
	
	@FXML
	private MenuItem menuItemServicos;
	
	@FXML
	private MenuItem menuItemBarbeiros;
	
	@FXML
	private MenuItem menuItemUsuarios;

	@FXML
	private Button btn_CadastrarCliente;
	
	@FXML
	private MenuItem menuItemBuscarClientes;

	@FXML
	private void onCadastrarClienteAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/Clientes.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Cadastro de Clientes");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onCadastrarServicosAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/ViewServicos.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Cadastro de Serviço");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onCadastrarBarbeirosAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/BarbeiroView.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Cadastro de Barbeiro");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onCadastrarUsuariosAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/UsuarioView.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Cadastro de Usuários");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onBuscarTodosClientesAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/ListarCliente.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Buscar Clientes");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onBuscarTodosBarbeirosAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/ListarBarbeiro.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Buscar Clientes");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onBuscarTodosServicosAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/ListarServicos.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Buscar Serviços");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onAgendarServicoAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/AgendaView.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Agendar Serviço");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
