package client.ui;

import client.logicfactories.LogicFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;


public class MainMenuController {

    @FXML
    Button btnQuickPlay;

    @FXML
    Button btnCreateLobby;

    @FXML
    Button btnFindLobby;

    @FXML
    Button btnJoinLobby;


    private URL getURL(String path){
        try {
            return new File("src/main/java/client/ui/guifiles/" + path).toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML

    void initialize() {
        btnQuickPlay.setOnAction(event -> {
            //Temp
            FourPlayerController controller = new FourPlayerController();
            controller.setGame(LogicFactory.getOnlineFourPlayerGame());
            JavaFXSceneFactory.generateStage(controller, getURL( "4-Player.fxml"), false, "Speelbord", 629, 0).show();

            btnQuickPlay.getScene().getWindow().hide();
        });

        btnCreateLobby.setOnAction(event -> {
            LobbyController controller = new LobbyController();
            JavaFXSceneFactory.generateStage(controller,getURL("LobbyView.fxml"), false, "Spellobby", 429, 431).show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });

        btnFindLobby.setOnAction(event -> {
            JavaFXSceneFactory.generateStage(new LobbyBrowserController(),  getURL("LobbyBrowser.fxml"), false, "Lobby Browser", 429, 600).show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });

    }

}
