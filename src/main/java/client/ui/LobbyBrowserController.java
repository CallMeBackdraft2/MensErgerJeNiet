package client.ui;

import client.domain.classes.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

class LobbyBrowserController {
    Player player;

    @FXML Button btnGoToMain;

    public LobbyBrowserController(Player player){
        this.player = player;
    }

    @FXML
    void initialize() {
        btnGoToMain.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                JavaFXSceneFactory.generateStage(new MainMenuController(player), getClass().getResource("guifiles/MainMenu.fxml"), false, "Hoofdmenu").show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });
    }
}
