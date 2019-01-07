package client.ui.controllers;

import client.domain.classes.Player;
import client.ui.JavaFXSceneFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.File;
import java.net.MalformedURLException;


public class LobbyBrowserController {
    Player player;

    @FXML
    Button btnGoToMain;

    public LobbyBrowserController(Player player) {
        this.player = player;
    }

    @FXML
    public void initialize() {
        btnGoToMain.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    JavaFXSceneFactory.generateStage(new MainMenuController(player), new File("src/main/java/client/ui/guifiles/MainMenu.fxml").toURI().toURL(), false, "Hoofdmenu").show();
                    btnGoToMain.getScene().getWindow().hide();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
