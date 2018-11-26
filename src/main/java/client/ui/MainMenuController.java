package client.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;


public class MainMenuController {

    @FXML
    Button btnQuickPlay;

    @FXML
    Button btnCreateLobby;

    @FXML
    Button btnFindLobby;

    @FXML
    Button btnJoinLobby;

    @FXML
    void initialize() {
        btnQuickPlay.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FourPlayerController controller = new FourPlayerController();

                JavaFXSceneFactory.generateStage(controller, getClass().getResource("src/main/java/client/ui/guifiles/4-Player.fxml"), false, "Speelbord", 629, 0).show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });

        btnCreateLobby.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                LobbyController controller = new LobbyController();

                JavaFXSceneFactory.generateStage(controller, getClass().getResource("src/main/java/client/ui/guifiles/LobbyView.fxml"), false, "Spellobby", 429, 431).show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });

        btnFindLobby.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                JavaFXSceneFactory.generateStage(new LobbyBrowserController(), getClass().getResource("src/main/java/client/ui/guifiles/LobbyBrowser.fxml"), false, "Lobby Browser", 429, 600).show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });

    }

}
