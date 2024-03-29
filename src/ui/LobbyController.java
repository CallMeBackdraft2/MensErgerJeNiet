package ui;

import domain.Classes.Lobby;
import domain.Classes.Player;
import domain.Enums.GameMode;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import logicFactories.LogicFactory;

import java.net.URL;
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
    Button btnReady;
    @FXML
    ChoiceBox cbGameMode;
    @FXML
    ImageView imgvBoardView;
    @FXML
    Button btnDelPlayer;



    @FXML
    void initialize() {
        gameLobby = new Lobby();
        btnReady.setText("Start Game");
        btnReady.setDisable(true);
        cbGameMode.setItems(FXCollections.observableArrayList(EnumSet.allOf(GameMode.class)));
        cbGameMode.getSelectionModel().select(0);
        cbLobbyType.setItems(FXCollections.observableArrayList("Local Game", new Separator(),"Online Game"));
        cbLobbyType.getSelectionModel().select(0);
        cbLobbyType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue == (Number)0){
                    btnReady.setText("Start Game");
                    gameLobby.setOnline(false);
                    btnAddPlayer.setDisable(false);
                    txtfPassword.setDisable(true);
                    txtfLobbyName.setDisable(true);
                } else {
                    btnReady.setText("Ready");
                    gameLobby.setOnline(true);
                    btnAddPlayer.setDisable(true);
                    txtfPassword.setDisable(false);
                    txtfLobbyName.setDisable(false);
                }
            }
        });

        cbGameMode.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue == (Number)0){
                    gameLobby.setGameMode(GameMode.FOURPLAYERBOARD);
                    imgvBoardView.setImage(new Image(getClass().getResourceAsStream("Images/4playerboard.JPG")));
                    checkPlayerCount();
                } else {
                    gameLobby.setGameMode(GameMode.SIXPLAYERBOARD);
                    imgvBoardView.setImage(new Image(getClass().getResourceAsStream("Images/6playerboard.JPG")));
                    checkPlayerCount();
                }

            }
        });

        btnLeaveLobby.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                JavaFXSceneFactory.generateStage(new MainMenuController(), getClass().getResource("guifiles/MainMenu.fxml"), false, "Hoofdmenu").show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });

        btnReady.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (gameLobby.isOnline()){
                    //todo online implementation
                } else {
                    FourPlayerController controller = new FourPlayerController();
                    controller.setGame(LogicFactory.getLocalFourPlayerGame(gameLobby));
                    JavaFXSceneFactory.generateStage(controller, getClass().getResource("guifiles/4-Player.fxml"), false, "Speelbord", 629, 0).show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                }
            }
        });

        btnDelPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameLobby.playerLeave((Player)lvPlayerList.getSelectionModel().getSelectedItem());
                updatePlayerList();
            }
        });

        btnAddPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialog<Pair<String, PlayerColor>> dialog = new Dialog<>();
                dialog.setTitle("Add Player");
                dialog.setHeaderText("Fill in the following information");

                ButtonType addPlayerButton = new ButtonType("Add Player", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(addPlayerButton, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField username = new TextField();
                username.setPromptText("Player Name");
                ChoiceBox<PlayerColor> playerColor = new ChoiceBox();
                if (gameLobby.getGameMode() == GameMode.FOURPLAYERBOARD){
                    playerColor.setItems( FXCollections.observableArrayList(checkAvailableColors()));

                } else {
                    playerColor.setItems( FXCollections.observableArrayList(checkAvailableColors()));
                }



                grid.add(new Label("Username:"), 0, 0);
                grid.add(username, 1, 0);
                grid.add(new Label("Password:"), 0, 1);
                grid.add(playerColor, 1, 1);


                Node loginButton = dialog.getDialogPane().lookupButton(addPlayerButton);
                loginButton.setDisable(true);

                username.textProperty().addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(!(((playerColor.getValue() == null || playerColor.getValue().toString().trim().isEmpty() ? 1 : 0) + (newValue.trim().isEmpty() ? 1 : 0)) == 0));
                });

                playerColor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(!(((newValue.toString().trim().isEmpty() ? 1 : 0) + (username.textProperty().getValue().trim().isEmpty() ? 1 : 0)) == 0));
                });

                dialog.getDialogPane().setContent(grid);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == addPlayerButton) {
                        return new Pair<String, PlayerColor>(username.getText(), playerColor.getValue());
                    }
                    return null;
                });

                Optional<Pair<String, PlayerColor>> result = dialog.showAndWait();

                result.ifPresent(player -> {
                    Player newPlayer = new Player(0,player.getKey());
                    newPlayer.setPlayerColor(player.getValue());
                    try {
                        gameLobby.playerJoin(newPlayer);
                    } catch (IllegalArgumentException exc) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Max Player Amount");
                        alert.setHeaderText("You tried exceeding the maximum allowed players for this gamemode");
                        alert.setContentText(exc.getMessage());
                        alert.showAndWait();
                    }
                    updatePlayerList();
                    checkPlayerCount();
                });


            }
        });
    }

    private EnumSet<PlayerColor> checkAvailableColors(){
        EnumSet<PlayerColor> pcEnumSet = EnumSet.allOf(PlayerColor.class);
        for (Player player: gameLobby.getPlayers()) {
            pcEnumSet.remove(player.getPlayerColor());
        }
        if (gameLobby.getGameMode() == GameMode.FOURPLAYERBOARD) {
            pcEnumSet.remove(PlayerColor.BLACK);
            pcEnumSet.remove(PlayerColor.PURPLE);
        }
        return pcEnumSet;
    }

    private void updatePlayerList(){
        lvPlayerList.setItems(FXCollections.observableArrayList(gameLobby.getPlayers()));
    }

    private void checkPlayerCount() {
        if ((gameLobby.getPlayers().size() == 4 && gameLobby.getGameMode() == GameMode.FOURPLAYERBOARD) || (gameLobby.getPlayers().size() == 6 && gameLobby.getGameMode() == GameMode.SIXPLAYERBOARD)){
            btnReady.setDisable(false);
        } else {
            btnReady.setDisable(true);
        }
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void playerJoin(Player player) {
        lobby.playerJoin(player);
    }
}
