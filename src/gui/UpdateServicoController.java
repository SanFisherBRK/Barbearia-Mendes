package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import BD.Conexao;
import Entities.Servicos;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UpdateServicoController {

    @FXML
    private TextField txt_Nome;
    @FXML
    private TextField txt_Descricao;
    @FXML
    private TextField txt_Preco;
    @FXML
    private Button btn_Atualizar;
    @FXML
    private Button btn_Limpar;
    @FXML
    private Servicos servico;

    @FXML
    public void preencherDadosServico(Servicos servico) {
        this.servico = servico;
        txt_Nome.setText(servico.getNome());
        txt_Descricao.setText(servico.getDescricao());
        txt_Preco.setText(String.valueOf(servico.getPreco()));
    }

    @FXML
    public void updateServico() throws SQLException {
        servico.setNome(txt_Nome.getText());
        servico.setDescricao(txt_Descricao.getText());
        servico.setPreco(Double.parseDouble(txt_Preco.getText()));

        String sql = "UPDATE servico SET nome = ?, descricao = ?, preco = ? WHERE idservico = ?";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement enviaParaOBanco = conn.prepareStatement(sql)) {

            if (servico.getNome().isEmpty() || servico.getDescricao().isEmpty() || servico.getPreco() == 0.0) {

                Alerts.showAlert("Atualizar Cliente", "Preencha todos os campos", "Tentar de novo", AlertType.INFORMATION);

            } else {
                enviaParaOBanco.setString(1, servico.getNome());
                enviaParaOBanco.setString(2, servico.getDescricao());
                enviaParaOBanco.setDouble(3, servico.getPreco());
                enviaParaOBanco.setInt(4, servico.getIdservico());

                enviaParaOBanco.executeUpdate();

                Alerts.showAlert("Atualizar Cliente", "Dados atualizados com sucesso", "ok", AlertType.INFORMATION);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void limparCampos() {
        txt_Nome.clear();
        txt_Descricao.clear();
        txt_Preco.clear();
    }
}
