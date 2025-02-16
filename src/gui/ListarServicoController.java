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
import Entities.Servicos;
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

public class ListarServicoController implements Initializable {
    @FXML
    private BorderPane bdp_Cliente;
    @FXML
    private Button btn_Atualizar;
    @FXML
    private Button btn_Buscar;
    @FXML
    private Button btn_Deletar;
    @FXML
    private TableColumn<Servicos, Integer> tbc_ID;
    @FXML
    private TableColumn<Servicos, String> tbc_Nome;
    @FXML
    private TableColumn<Servicos, String> tbc_Descricao;
    @FXML
    private TableColumn<Servicos, Double> tbc_Preco;
    @FXML
    private TableView<Servicos> tbv_Servicos;
    @FXML
    private TextField txt_Bucar;
    
    private Servicos servicoSelecionado;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbv_Servicos.setItems(FXCollections.observableArrayList(buscaTodos()));
        
        tbv_Servicos.setRowFactory(tv -> {
            TableRow<Servicos> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    servicoSelecionado = row.getItem();
                    if (event.getClickCount() == 2) {
                        abrirJanelaUpdateServico(servicoSelecionado);
                    }
                }
            });
            return row;
        });

        btn_Atualizar.setOnAction(event -> {
            servicoSelecionado = tbv_Servicos.getSelectionModel().getSelectedItem();
            if (servicoSelecionado != null) {
                abrirJanelaUpdateServico(servicoSelecionado);
            } else {
                Alerts.showAlert("Atualizar Serviço", "Nenhum serviço selecionado", "Selecione um serviço para atualizar", AlertType.WARNING);
            }
        });
    }
    
//==========================================================================================================================================================
    
    @FXML
    public void abrirJanelaUpdateServico(Servicos servico) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateServico.fxml"));
            Parent root = loader.load();

            UpdateServicoController controller = loader.getController();
            controller.preencherDadosServico(servico);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Atualizar Serviço");
            stage.setScene(new Scene(root));

            stage.setOnHiding(event -> recarregarTabela());

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//==========================================================================================================================================================    

    @FXML
    private void recarregarTabela() {
        tbv_Servicos.setItems(FXCollections.observableArrayList(buscaTodos()));
    }
    
//==========================================================================================================================================================    
    
    @FXML
    public void buscaNome() {
        String nome = txt_Bucar.getText();
        tbv_Servicos.setItems(FXCollections.observableArrayList(buscaPorNome(nome)));
    }
    
//==========================================================================================================================================================    

    @FXML
    public List<Servicos> buscaPorNome(String nome) {
        tbc_ID.setCellValueFactory(new PropertyValueFactory<>("idservico"));
        tbc_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbc_Descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbc_Preco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        List<Servicos> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servico WHERE nome LIKE ?";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement buscar = conn.prepareStatement(sql)) {

            buscar.setString(1, "%" + nome + "%");

            try (ResultSet rs = buscar.executeQuery()) {
                while (rs.next()) {
                    Servicos servico = new Servicos();
                    servico.setIdservico(rs.getInt("idservico"));
                    servico.setNome(rs.getString("nome"));
                    servico.setDescricao(rs.getString("descricao"));
                    servico.setPreco(rs.getDouble("preco"));
                    servicos.add(servico);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicos;
    }
    
//==========================================================================================================================================================    

    @FXML
    public List<Servicos> buscaTodos() {
        tbc_ID.setCellValueFactory(new PropertyValueFactory<>("idservico"));
        tbc_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbc_Descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbc_Preco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        List<Servicos> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servico";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement buscar = conn.prepareStatement(sql);
             ResultSet rs = buscar.executeQuery()) {

            while (rs.next()) {
                Servicos servico = new Servicos();
                servico.setIdservico(rs.getInt("idservico"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getDouble("preco"));

                servicos.add(servico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicos;
    }
    
//==========================================================================================================================================================    
}
