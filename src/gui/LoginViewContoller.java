package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import BD.Conexao;
import Entities.Usuarios;
import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewContoller {

    @FXML
    private Button btn_login;

    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_user;

    @FXML
    private void onLogaAction(ActionEvent event) {

        if (txt_user.getText().isEmpty() || txt_password.getText().isEmpty()) {
            Alerts.showAlert("Login", "Preencha todos os campos", "ok", AlertType.INFORMATION);
            return;
        }

        String email = txt_user.getText();
        String senha = txt_password.getText();

        if (authenticate(email, senha)) {
            try {
            	
            	//Obtem uma rreferência do Stage atual
            	Stage stage = (Stage) btn_login.getScene().getWindow();
            	stage.close();//Fecha a janela de login
            	
                Parent root = FXMLLoader.load(getClass().getResource("/gui/View.fxml"));
                Stage newStage = new Stage();
                newStage.setTitle("Barbearia Mendes");
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alerts.showAlert("Login", "Email ou senha incorretos", "ok", AlertType.ERROR);
        }
    }

    private boolean authenticate(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (Connection conn = new Conexao().getConnection();
             PreparedStatement enviaParaOBanco = conn.prepareStatement(sql)) {
            enviaParaOBanco.setString(1, email);
            enviaParaOBanco.setString(2, senha);

            ResultSet resultSet = enviaParaOBanco.executeQuery();
            return resultSet.next();  // Se houver algum resultado, o email e a senha são válidos
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
