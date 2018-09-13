package ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;


public class MainMenuController {

    @FXML
    Button btnQuickPlay;

    @FXML
    Button btnCreateLobby;

    @FXML
    Button btnFindLobby;

    @FXML
    void initialize(){
        btnQuickPlay.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                JavaFXSceneFactory.generateStage(getClass().getResource("4-Player.fxml"),false,"Speelbord",629).show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });

        btnCreateLobby.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                JavaFXSceneFactory.generateStage(getClass().getResource("LobbyView.fxml"), false, "Spellobby").show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });

        btnFindLobby.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                JavaFXSceneFactory.generateStage(getClass().getResource("LobbyBrowser.fxml"),false, "Lobby Browser").show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });

    }

}
