package client.ui.controllers;

import client.domain.classes.LobbyView;
import client.domain.classes.Player;
import client.logicfactories.LogicFactory;
import client.ui.FourPlayerController;
import client.ui.JavaFXSceneFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public class MainMenuController {

    Player player;

    @FXML Button btnQuickPlay;
    @FXML Button btnCreateLobby;
    @FXML Button btnFindLobby;
    @FXML Button btnJoinLobby;
    @FXML Button btnScenarios;


    public MainMenuController(Player player){
        this.player = player;
    }

    private URL getURL(String path){
        try {
            return new File("src/main/java/client/ui/guifiles/" + path).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    public void initialize() {
        btnQuickPlay.setOnAction(event -> {
            //Temp
            FourPlayerController controller = new FourPlayerController(player);
            JavaFXSceneFactory.generateStage(controller, getURL( "4-Player.fxml"), false, "Speelbord", 629, 0).show();
            controller.setGame(LogicFactory.getOnlineFourPlayerGame());
            hide();
        });

        btnCreateLobby.setOnAction(event -> {
            LobbyController controller = new LobbyController(player, new LobbyView(player));
            JavaFXSceneFactory.generateStage(controller,getURL("LobbyView.fxml"), false, "Spellobby", 429, 431).show();
            hide();
        });

        btnFindLobby.setOnAction(event -> {
            LobbyBrowserController controller = new LobbyBrowserController(player) ;
            JavaFXSceneFactory.generateStage(controller,  getURL("LobbyBrowser.fxml"), false, "Lobby Browser", 429, 600).show();
            controller.realInit();
            hide();
        });

        btnScenarios.setOnAction(event -> {
            ScenerioListController controller = new ScenerioListController();
            JavaFXSceneFactory.generateStage(controller,getURL("ScenariosView.fxml"),false,"Scenarios").show();
            controller.realInit();
            hide();
        });
    }

    private void hide() {
        btnQuickPlay.getScene().getWindow().hide();
    }

}
