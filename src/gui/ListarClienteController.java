package gui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import BD.Conexao;
import Entities.Clientes;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class ListarClienteController implements Initializable {
	

    @FXML
    private BorderPane bdp_Cliente;

    @FXML
    private Button btn_Atualizar;

    @FXML
    private Button btn_Buscar;

    @FXML
    private Button btn_Deletar;

    @FXML
    private TableColumn<Clientes, String> tbc_CPF;

    @FXML
    private TableColumn<Clientes, String> tbc_Email;

    @FXML
    private TableColumn<Clientes, String> tbc_Endereco;

    @FXML
    private TableColumn<Clientes, Integer> tbc_ID;

    @FXML
    private TableColumn<Clientes, String> tbc_Nome;

    @FXML
    private TableColumn<Clientes, String> tbc_Telefone;

    @FXML
    private TableView<Clientes> tbv_Cliente;

    @FXML
    private TextField txf_Bucar;
    
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        tbv_Cliente.setItems(FXCollections.observableArrayList(buscaTodos()));
    }

    
    @FXML
    public List<Clientes> buscaTodos() {
    	
    	tbc_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbc_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbc_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbc_Telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tbc_Endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tbc_CPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    	
    	
        List<Clientes> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement buscar = conn.prepareStatement(sql);
             ResultSet rs = buscar.executeQuery()) {

            while (rs.next()) {
                Clientes cliente = new Clientes();
                cliente.setId(rs.getInt("idcliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setCpf(rs.getString("cpf"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }
}

				
	
