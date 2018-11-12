package ui;

import domain.Classes.Lobby;
import domain.Classes.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

import java.util.List;


public class LobbyController {


    private Lobby lobby;
    Lobby gameLobby;

    @FXML
    Button btnLeaveLobby;
    @FXML
    Button btnAddPlayer;
    @FXML
    ChoiceBox cbLobbyType;
    @FXML
    TextField txtfPassword;
    @FXML
    TextField txtfLobbyName;

    @FXML
    void initialize() {
        gameLobby = new Lobby();


        cbLobbyType.setItems(FXCollections.observableArrayList("Local Game", new Separator(),"Online Game"));
        cbLobbyType.setValue("Local Game");

        cbLobbyType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue == (Number)0){
                    btnAddPlayer.setDisable(false);
                    txtfPassword.setDisable(true);
                    txtfLobbyName.setDisable(true);
                } else {
                    btnAddPlayer.setDisable(true);
                    txtfPassword.setDisable(false);
                    txtfLobbyName.setDisable(false);
                }
            }
        });

        btnLeaveLobby.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                JavaFXSceneFactory.generateStage(new MainMenuController(), getClass().getResource("guifiles/MainMenu.fxml"), false, "Hoofdmenu").show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });

        btnAddPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void playerJoin(Player player) {
        lobby.playerJoin(player);
    }
}
