package ui;

import domain.Classes.Lobby;
import domain.Classes.Player;
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
//        btnJoinLobby.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent event) {
//                SixPlayerController controller = new SixPlayerController();
//                //todo de-hardcode this and get player date from something like log-on or registration
//
//                JavaFXSceneFactory.generateStage(controller, getClass().getResource("guifiles/6-Player.fxml"), false, "Speelbord", 629, 0).show();
//                ((Node) (event.getSource())).getScene().getWindow().hide();
//            }
//        });

        btnQuickPlay.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FourPlayerController controller = new FourPlayerController();
                //todo de-hardcode this and get player date from something like log-on or registration

                JavaFXSceneFactory.generateStage(controller, getClass().getResource("guifiles/4-Player.fxml"), false, "Speelbord", 629, 0).show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });

        btnCreateLobby.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                LobbyController controller = new LobbyController();
                //todo de-hardcode this and get player date from something like log-on or registration

                JavaFXSceneFactory.generateStage(controller, getClass().getResource("guifiles/LobbyView.fxml"), false, "Spellobby", 429, 431).show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });

        btnFindLobby.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                JavaFXSceneFactory.generateStage(new LobbyBrowserController(), getClass().getResource("guifiles/LobbyBrowser.fxml"), false, "Lobby Browser", 429, 600).show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });

    }

}
