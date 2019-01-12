package client.ui.controllers;

import client.domain.classes.Lobby;
import client.domain.classes.Player;
import client.domain.enums.GameMode;
import client.domain.enums.PlayerColor;
import client.ui.FourPlayerController;
import client.ui.JavaFXSceneFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import client.logicfactories.LogicFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;
import java.util.Optional;


public class LobbyController {

    private Lobby lobby;
    private Player player;

    @FXML Button btnLeaveLobby;
    @FXML Button btnAddPlayer;
    @FXML ChoiceBox cbLobbyType;
    @FXML TextField txtfPassword;
    @FXML TextField txtfLobbyName;
    @FXML ListView lvPlayerList;
    @FXML Button btnReady;
    @FXML ChoiceBox cbGameMode;
    @FXML ImageView imgvBoardView;
    @FXML Button btnDelPlayer;

    public LobbyController(Player player, Lobby lobby){
        this.player = player;
        this.lobby = lobby;
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
        player.setPlayerColor(PlayerColor.RED);
        updatePlayerList();

        btnReady.setText("Start Game");
        btnReady.setDisable(true);

        cbGameMode.setItems(FXCollections.observableArrayList(EnumSet.allOf(GameMode.class)));
        cbGameMode.getSelectionModel().select(0);
        cbLobbyType.setItems(FXCollections.observableArrayList("Local Game", new Separator(),"Online Game"));
        cbLobbyType.getSelectionModel().select(0);

        cbLobbyType.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> changeLobbyType(newValue));

        cbGameMode.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> changedGameMode(newValue));

        btnLeaveLobby.setOnAction(event -> {
            JavaFXSceneFactory.generateStage(new MainMenuController(player), getURL( "MainMenu.fxml"), false, "Hoofdmenu").show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });

        btnReady.setOnAction(event -> {

            FourPlayerController controller = new FourPlayerController(player);

            if (lobby.isOnline()){
                JavaFXSceneFactory.generateStage(controller, getURL( "4-Player.fxml"), false, "Speelbord", 629, 0).show();
                controller.setGame(LogicFactory.getOnlineFourPlayerGame());

            }
            else {
                JavaFXSceneFactory.generateStage(controller, getURL( "4-Player.fxml"), false, "Speelbord", 629, 0).show();
                controller.setGame(LogicFactory.getLocalFourPlayerGame(lobby));
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });

        btnDelPlayer.setOnAction(event -> {
            lobby.playerLeave((Player)lvPlayerList.getSelectionModel().getSelectedItem());
            updatePlayerList();
        });

        btnAddPlayer.setOnAction(event -> {
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
            playerColor.setItems( FXCollections.observableArrayList(checkAvailableColors()));



            grid.add(new Label("Username:"), 0, 0);
            grid.add(username, 1, 0);
            grid.add(new Label("Password:"), 0, 1);
            grid.add(playerColor, 1, 1);


            Node loginButton = dialog.getDialogPane().lookupButton(addPlayerButton);
            loginButton.setDisable(true);

            handleAddPlayer(dialog, addPlayerButton, grid, username, playerColor, loginButton);
        });
    }

    private void handleAddPlayer(Dialog<Pair<String, PlayerColor>> dialog, ButtonType addPlayerButton, GridPane grid, TextField username, ChoiceBox<PlayerColor> playerColor, Node loginButton) {
        if(playerColor.getValue()!=null) {
            username.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable((((playerColor.getValue() != null ||
                    playerColor.getValue().toString().trim().isEmpty() ? 1 : 0) + (newValue.trim().isEmpty() ? 1 : 0)) != 0)));
        }
        playerColor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                loginButton.setDisable((((newValue.toString().trim().isEmpty() ? 1 : 0) + (username.textProperty().getValue().trim().isEmpty() ? 1 : 0)) != 0)));

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
            tryJoinNewPlayer(newPlayer);
            updatePlayerList();
            checkPlayerCount();
        });
    }

    private void tryJoinNewPlayer(Player newPlayer) {
        try {
            lobby.playerJoin(newPlayer);
        } catch (IllegalArgumentException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Max Player Amount");
            alert.setHeaderText("You tried exceeding the maximum allowed players for this gamemode");
            alert.setContentText(exc.getMessage());
            alert.showAndWait();
        }
    }

    private void changedGameMode(Number newValue) {
        if(newValue == (Number)0){
            lobby.setGameMode(GameMode.FOURPLAYERBOARD);
            imgvBoardView.setImage(new Image(getClass().getResourceAsStream("Images/4playerboard.JPG")));
            checkPlayerCount();
        } else {
            lobby.setGameMode(GameMode.SIXPLAYERBOARD);
            imgvBoardView.setImage(new Image(getClass().getResourceAsStream("Images/6playerboard.JPG")));
            checkPlayerCount();
        }
    }

    private void changeLobbyType(Number newValue) {
        if (newValue == (Number) 0) {
            btnReady.setText("Start Game");
            lobby.setOnline(false);
            btnAddPlayer.setDisable(false);
            txtfPassword.setDisable(true);
            txtfLobbyName.setDisable(true);
        } else {
            btnReady.setText("Ready");
            lobby.setOnline(true);
            btnAddPlayer.setDisable(true);
            txtfPassword.setDisable(false);
            txtfLobbyName.setDisable(false);
        }
    }

    private EnumSet<PlayerColor> checkAvailableColors(){
        EnumSet<PlayerColor> pcEnumSet = EnumSet.allOf(PlayerColor.class);
        for (Player player: lobby.getPlayers()) {
            pcEnumSet.remove(player.getPlayerColor());
        }
        if (lobby.getGameMode() == GameMode.FOURPLAYERBOARD) {
            pcEnumSet.remove(PlayerColor.BLACK);
            pcEnumSet.remove(PlayerColor.PURPLE);
        }
        return pcEnumSet;
    }

    private void updatePlayerList(){
        lvPlayerList.setItems(FXCollections.observableArrayList(lobby.getPlayers()));
    }

    private void checkPlayerCount() {
        if ((lobby.getPlayers().size() == 4 && lobby.getGameMode() == GameMode.FOURPLAYERBOARD) || (lobby.getPlayers().size() == 6 && lobby.getGameMode() == GameMode.SIXPLAYERBOARD)){
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
