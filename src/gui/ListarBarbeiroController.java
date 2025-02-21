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
import Entities.Barbeiros;
import Entities.Clientes;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListarBarbeiroController implements Initializable {

    @FXML
    private BorderPane bdp_bareiro;

    @FXML
    private Button btn_Atualizar;

    @FXML
    private Button btn_Buscar;

    @FXML
    private Button btn_Deletar;

    @FXML
    private TableColumn<Barbeiros, String> tbc_CPF;

    @FXML
    private TableColumn<Barbeiros, String> tbc_Email;

    @FXML
    private TableColumn<Barbeiros, String> tbc_Endereco;

    @FXML
    private TableColumn<Barbeiros, Integer> tbc_ID;

    @FXML
    private TableColumn<Barbeiros, String> tbc_Nome;

    @FXML
    private TableColumn<Barbeiros, String> tbc_Telefone;

    @FXML
    private TableView<Barbeiros> tbv_barbeiro;

    @FXML
    private TextField txt_Buscar;
    
    private Barbeiros barbeiroSelecionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
        tbv_barbeiro.setItems(FXCollections.observableArrayList(buscaTodos()));
        
        tbv_barbeiro.setRowFactory(tv -> {
            TableRow<Barbeiros> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                	barbeiroSelecionado = row.getItem();
                    if (event.getClickCount() == 2) {
                        abrirJanelaUpdateBarbeiro(barbeiroSelecionado);
                    }
                }
            });
            return row;
        });

        btn_Atualizar.setOnAction(event -> {
            // Atualiza o clienteSelecionado com a linha atualmente selecionada na tabela
        	barbeiroSelecionado = tbv_barbeiro.getSelectionModel().getSelectedItem();
            if (barbeiroSelecionado != null) {
                abrirJanelaUpdateBarbeiro(barbeiroSelecionado);
            } else {
                Alerts.showAlert("Atualizar Barbeiro", "Nenhum barbeiro selecionado", "Selecione um cliente para atualizar", AlertType.WARNING);
            }
        });
    }
    
//=======================================================================================================================================================    
   
    @FXML
    public void abrirJanelaUpdateBarbeiro(Barbeiros barbeiro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateBarbeiro.fxml"));
            Parent root = loader.load();

            UpdateBarbeiroController controller = loader.getController();
            controller.preencherDadosBarbeiro(barbeiro);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Atualizar Barbeiro");
            stage.setScene(new Scene(root));

            // Adicione um listener para quando a janela for fechada
            stage.setOnHiding(event -> recarregarTabela());

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//=======================================================================================================================================================

 // Método para recarregar a tabela após atualização
    @FXML
    private void recarregarTabela() {
    	tbv_barbeiro.setItems(FXCollections.observableArrayList(buscaTodos()));
    }
    
   
//=======================================================================================================================================================    
    
    @FXML
    public void buscarNomeBarbeiro() {
        String nome = txt_Buscar.getText();
        tbv_barbeiro.setItems(FXCollections.observableArrayList(buscaBarbeiroPorNome(nome)));
    }
    
//=======================================================================================================================================================
 
    @FXML
    public List<Barbeiros> buscaBarbeiroPorNome(String nome) {
    	
        tbc_ID.setCellValueFactory(new PropertyValueFactory<>("idbarbeiro"));
        tbc_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbc_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbc_Telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tbc_Endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tbc_CPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        

        List<Barbeiros> barbeiros = new ArrayList<>();
        String sql = "SELECT * FROM barbeiro WHERE nome LIKE ?";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement buscar = conn.prepareStatement(sql)) {

            buscar.setString(1, "%" + nome + "%");

            try (ResultSet rs = buscar.executeQuery()) {
                while (rs.next()) {
                	Barbeiros barbeiro = new Barbeiros();
                	barbeiro.setIdbarbeiro(rs.getInt("idbarbeiro"));
                	barbeiro.setNome(rs.getString("nome"));
                	barbeiro.setEmail(rs.getString("email"));
                	barbeiro.setTelefone(rs.getString("telefone"));
                	barbeiro.setEndereco(rs.getString("endereco"));
                	barbeiro.setCpf(rs.getString("cpf"));
                	barbeiros.add(barbeiro);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return barbeiros;
    }
    
  //=======================================================================================================================================================    
    
    private void configureTableColumns() {
        tbc_ID.setCellValueFactory(new PropertyValueFactory<>("idbarbeiro"));
        tbc_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbc_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbc_Telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tbc_Endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tbc_CPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    }

    @FXML
    public List<Barbeiros> buscaTodos() {
        List<Barbeiros> barbeiros = new ArrayList<>();
        String sql = "SELECT * FROM barbeiro";
        try (Connection conn = new Conexao().getConnection();
             PreparedStatement buscar = conn.prepareStatement(sql);
             ResultSet rs = buscar.executeQuery()) {

            while (rs.next()) {
                Barbeiros barbeiro = new Barbeiros();
                barbeiro.setIdbarbeiro(rs.getInt("idbarbeiro"));
                barbeiro.setNome(rs.getString("nome"));
                barbeiro.setEmail(rs.getString("email"));
                barbeiro.setTelefone(rs.getString("telefone"));
                barbeiro.setEndereco(rs.getString("endereco"));
                barbeiro.setCpf(rs.getString("cpf"));
                barbeiros.add(barbeiro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return barbeiros;
    }
}
