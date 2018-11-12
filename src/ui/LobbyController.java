package ui;

import domain.Classes.Lobby;
import domain.Classes.Player;
import domain.Enums.PlayerColor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;


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
    ListView lvPlayerList;

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
                Dialog<Pair<String, PlayerColor>> dialog = new Dialog<>();
                dialog.setTitle("Add Player");
                dialog.setHeaderText("Fill in the following information");

                ButtonType loginButtonType = new ButtonType("Add Player", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField username = new TextField();
                username.setPromptText("Player Name");
                ChoiceBox<PlayerColor> playerColor = new ChoiceBox();
                playerColor.setItems( FXCollections.observableArrayList(EnumSet.allOf(PlayerColor.class)));

                grid.add(new Label("Username:"), 0, 0);
                grid.add(username, 1, 0);
                grid.add(new Label("Password:"), 0, 1);
                grid.add(playerColor, 1, 1);

                dialog.getDialogPane().setContent(grid);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        return new Pair<String, PlayerColor>(username.getText(), playerColor.getValue());
                    }
                    return null;
                });

                Optional<Pair<String, PlayerColor>> result = dialog.showAndWait();

                result.ifPresent(player -> {
                    Player newPlayer = new Player(0,player.getKey());
                    newPlayer.setPlayercolor(player.getValue());
                    gameLobby.playerJoin(newPlayer);
                    updatePlayerList();
                });


            }
        });
    }

    public void updatePlayerList(){
        lvPlayerList.setItems(FXCollections.observableArrayList(gameLobby.getPlayers()));
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void playerJoin(Player player) {
        lobby.playerJoin(player);
    }
}
