package client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegisterViewController {
    @FXML Button bttn_register;
    @FXML TextField txt_username;
    @FXML TextField txt_password;
    @FXML TextField txt_repeatPassword;

    @FXML
    void initialize() {
        bttn_register.setOnAction(event -> {

        });
    }
}
