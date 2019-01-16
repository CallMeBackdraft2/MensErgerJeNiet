package client.ui.controllers;

import client.domain.classes.Player;
import client.logic.onlineimplementation.ConnectionCaller;
import client.ui.JavaFXSceneFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.MalformedURLException;


public class LoginViewController {
    private ConnectionCaller connectionCaller;
    @FXML Button bttn_register;
    @FXML Button bttn_login;
    @FXML TextField txt_password;
    @FXML TextField txt_username;

    @FXML
    public void initialize(){

       connectionCaller  = new ConnectionCaller();
        bttn_login.setOnAction(event -> {

            boolean succes =true;
            try {
                  connectionCaller.login(txt_username.getText(),txt_password.getText()) ;
            } catch (Exception e) {
                succes =false;
                MessageShower.showError(e);
            }

            if(succes) {
                Player player = new Player(txt_username.getText());
                MainMenuController controller = new MainMenuController(player);
                try {
                    JavaFXSceneFactory.generateStage(controller, new File("src/main/java/client/ui/guifiles/MainMenu.fxml").toURI().toURL(), false, "Hoofdmenu").show();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                bttn_register.getScene().getWindow().hide();

            }

        });

        bttn_register.setOnAction(event -> {
            RegisterViewController controller = new RegisterViewController();
            try {
                JavaFXSceneFactory.generateStage(controller, new File("src/main/java/client/ui/guifiles/RegisterView.fxml").toURI().toURL(), false, "Registreer").show();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            bttn_register.getScene().getWindow().hide();
        });
    }
}
