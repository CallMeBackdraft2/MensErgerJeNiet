package client.ui.controllers;

import client.domain.classes.LobbyView;
import client.domain.classes.Player;
import client.logic.onlineimplementation.ConnectionCaller;
import client.ui.JavaFXSceneFactory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;


public class LobbyBrowserController {


    @FXML
    Button btnJoinSelectedLobby;
    private Player player;
    private ConnectionCaller connectionCaller;
    @FXML
    private Button btnGoToMain;
    @FXML
    private ListView<LobbyView> listView;


    public LobbyBrowserController(Player player) {
        this.player = player;
        connectionCaller = new ConnectionCaller();
    }

    @FXML
    public void initialize() {
        btnGoToMain.setOnAction(event -> {
            try {
                JavaFXSceneFactory.generateStage(new MainMenuController(player), new File("src/main/java/client/ui/guifiles/MainMenu.fxml").toURI().toURL(), false, "Hoofdmenu").show();

                btnGoToMain.getScene().getWindow().hide();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            try {
              listView.getItems().clear();
                listView.getItems().addAll(connectionCaller.getLobbies());
            } catch (Exception e) {
                MessageShower.showError(e);
            }
        }));
        timeline.setCycleCount(1);
        timeline.play();


        listView.onMouseReleasedProperty().setValue(event -> {
            LobbyView index = listView.getSelectionModel().getSelectedItem();
            try {
                connectionCaller.joinLobby(index.getName());
                LobbyView lobbyView = new LobbyView();
                lobbyView.setOnline(true);
                LobbyController lb =  new LobbyController(new Player(""),lobbyView);
                lb.setLocked(true);
                JavaFXSceneFactory.generateStage(lb, new File("src/main/java/client/ui/guifiles/LobbyView.fxml").toURI().toURL(), false, "Hoofdmenu").show();
                btnGoToMain.getScene().getWindow().hide();
            } catch (Exception e) {
                MessageShower.showError(e);
            }
        });

    }

    public void realInit() {


    }
}
