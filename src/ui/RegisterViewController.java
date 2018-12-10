package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegisterViewController {
    @FXML TextField txt_username;
    @FXML TextField txt_password;
    @FXML TextField txt_repeatPassword;

    public void bttn_register_click(ActionEvent event) {
        if (txt_password.getText().equals(txt_repeatPassword.getText())){

        }
    }
}
