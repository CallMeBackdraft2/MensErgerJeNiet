package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LobbyController {

    @FXML
    Button btnLeaveLobby;

    @FXML
    void initialize(){
        btnLeaveLobby.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                JavaFXSceneFactory.generateStage(getClass().getResource("guifiles/MainMenu.fxml"),false, "Hoofdmenu").show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });
    }
}
