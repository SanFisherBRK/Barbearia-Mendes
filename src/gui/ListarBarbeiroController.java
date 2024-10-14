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
import Entities.Barbeiros;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

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
    private TextField txf_Buscar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
        tbv_barbeiro.setItems(FXCollections.observableArrayList(buscaTodos()));
    }

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
