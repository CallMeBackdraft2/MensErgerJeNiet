package client.ui.controllers;

import client.ui.JavaFXSceneFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.MalformedURLException;

public class RegisterViewController {
    @FXML Button bttn_back;
    @FXML Button bttn_register;
    @FXML TextField txt_username;
    @FXML TextField txt_password;
    @FXML TextField txt_repeatPassword;

    @FXML
    public void initialize() {
        bttn_register.setOnAction(event -> {

        });

        bttn_back.setOnAction(event -> {
            LoginViewController controller = new LoginViewController();
            try {
                JavaFXSceneFactory.generateStage(controller, new File("src/main/java/client/ui/guifiles/LoginView.fxml").toURI().toURL(), false, "Login").show();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            bttn_back.getScene().getWindow().hide();
        });
    }
}