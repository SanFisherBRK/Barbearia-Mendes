package gui;

import java.io.IOException;
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
import Entities.Usuarios;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListarUsuariosController implements Initializable {
    @FXML
    private BorderPane bdp_Usuarios;
    @FXML
    private Button btn_Atualizar;
    @FXML
    private Button btn_Buscar;
    @FXML
    private Button btn_Deletar;

    @FXML
    private TableColumn<Usuarios, String> tbc_Email;
    @FXML
    private TableColumn<Usuarios, String> tbc_Senha;
    @FXML
    private TableColumn<Usuarios, Integer> tbc_ID;
    @FXML
    private TableColumn<Usuarios, String> tbc_Nome;
    @FXML
    private TableColumn<Usuarios, String> tbc_Login;
    @FXML
    private TableView<Usuarios> tbv_Usuarios;
    @FXML
    private TextField txt_Bucar;
    
    private Usuarios usuarioSelecionado;
//=======================================================================================================================================================
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	tbv_Usuarios.setItems(FXCollections.observableArrayList(buscaTodos()));

    /*	tbv_Usuarios.setRowFactory(tv -> {
            TableRow<Usuarios> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                	usuarioSelecionado = row.getItem();
                    if (event.getClickCount() == 2) {
                        abrirJanelaUpdateUsuarios(usuarioSelecionado);
                    }
                }
            });
            return row;
        });

        btn_Atualizar.setOnAction(event -> {
            // Atualiza o clienteSelecionado com a linha atualmente selecionada na tabela
        	usuarioSelecionado = tbv_Usuarios.getSelectionModel().getSelectedItem();
            if (usuarioSelecionado != null) {
                abrirJanelaUpdateUsuarios(usuarioSelecionado);
            } else {
                Alerts.showAlert("Atualizar usuário", "Nenhum usuário selecionado", "Selecione um usuário para atualizar", AlertType.WARNING);
            }
        });
    */
    }
//=======================================================================================================================================================
    
   /* @FXML
    public void abrirJanelaUpdateUsuarios(Usuarios usuarios) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateCliente.fxml"));
            Parent root = loader.load();

            //UpdateCliente controller = loader.getController();
            //controller.preencherDadosUsuarios(usuarios);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Atualizar Usuario");
            stage.setScene(new Scene(root));

            // Adicione um listener para quando a janela for fechada
            stage.setOnHiding(event -> recarregarTabela());

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */

//=======================================================================================================================================================
    
    // Método para recarregar a tabela após atualização
    @FXML
    private void recarregarTabela() {
        tbv_Usuarios.setItems(FXCollections.observableArrayList(buscaTodos()));
    }
    
   
//=======================================================================================================================================================    
   
    @FXML
    public void buscarNomeUsuario() {
        String nome = txt_Bucar.getText();
        tbv_Usuarios.setItems(FXCollections.observableArrayList(buscaUsuarioPorNome(nome)));
    }
    
//=======================================================================================================================================================    
    
    @FXML
    public List<Usuarios> buscaUsuarioPorNome(String nome) {
    	
    	tbc_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbc_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbc_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbc_Login.setCellValueFactory(new PropertyValueFactory<>("login"));
        tbc_Senha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        

        List<Usuarios> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nome LIKE ?";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement buscar = conn.prepareStatement(sql)) {

            buscar.setString(1, "%" + nome + "%");

            try (ResultSet rs = buscar.executeQuery()) {
                while (rs.next()) {
                	
                	Usuarios usuario = new Usuarios();
                    usuario.setId(rs.getInt("idusuario"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setSenha(rs.getString("senha"));
                	usuarios.add(usuario);
                	
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }
    
  //=======================================================================================================================================================    

    @FXML
    public List<Usuarios> buscaTodos() {
        tbc_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbc_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbc_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbc_Login.setCellValueFactory(new PropertyValueFactory<>("login"));
        tbc_Senha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        

        List<Usuarios> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement buscar = conn.prepareStatement(sql);
             ResultSet rs = buscar.executeQuery()) {

            while (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setId(rs.getInt("idusuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }
}
