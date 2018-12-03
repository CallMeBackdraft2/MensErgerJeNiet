package client.ui;

import client.domain.classes.Pawn;
import client.domain.classes.Tile;
import client.domain.enums.PlayerColor;
import client.logic.interfaces.Game;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;


public class FourPlayerController {
    @FXML
    ImageView imgDice;
    @FXML
    Button btnLeaveGame;
    @FXML
    Button btnThrowDice;
    @FXML
    AnchorPane boardPane;
    @FXML
    TextField messageField;

    @FXML
    Circle turnCircle;

    @FXML
    ListView chatListView;
    @FXML
    ListView playersListView;

    Game game;
    Circle selectedPawn = null;

    private Dictionary<Tile, Circle> tileCircles = new Hashtable<>();
    private Dictionary<Pawn, Circle> pawnCircles = new Hashtable<>();


    private boolean isThrown;
    private Pawn chosenPawn;
    private int lastAmountDiceRolled = 0;

    @FXML
    void initialize() {
        addAllEventHandlers();
        populatePlayingField();
        playersListView.setItems(FXCollections.observableArrayList(game.getPlayers()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (game.getNeedsUpdate()) {
                updateBoard();
                updateMessages();
                updateDice(false);
                //lastAmountDiceRolled = game.getDiceAmountRolled();
                game.setNeedsUpdate(false);
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateMessages(){
        messageField.clear();
        ObservableList<String> s = FXCollections.observableArrayList(game.getMessages());
        chatListView.setItems(s);
    }

    private URL getURL(String path) {
        try {
            return new File("src/main/java/client/ui/" + path).toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setGame(Game game) {
        this.game = game;
    }

    private int getRadius(String type) {
        if (type.equals("WLK")) {
            return 20;
        } else {
            return 15;
        }
    }

    private Circle getPawnCircle(Pawn pawn) {
        Enumeration<Pawn> pawns = pawnCircles.keys();

        while (pawns.hasMoreElements()) {
            Pawn t = pawns.nextElement();
            if (t.getFullId().equals(pawn.getFullId())) {
                return this.pawnCircles.get(t);
            }
        }
        return null;
    }

    private Circle getTileCircle(Tile tile) {
        Enumeration<Tile> tiles = tileCircles.keys();

        while (tiles.hasMoreElements()) {
            Tile t = tiles.nextElement();
            if (t.getFullId().equals(tile.getFullId())) {
                return tileCircles.get(t);
            }
        }
        return null;
    }

    private void pawnPressed(Pawn pawn) {

        chosenPawn = pawn;
        clearSelection();
        if (game.isYourTurn()) {
            chosenPawn = pawn;
            Tile possibleMove = game.getPossibleMove(pawn.getFullId());
            if (pawn.getPlayerColor().getValue() != game.getCurrentPlayerId()) {

                showError(new IllegalArgumentException("Not " + pawn.getPlayerColor() + "'s turn"));
                return;
            }

            if (possibleMove == null) {

                return;
            }
            getTileCircle(possibleMove).setStrokeWidth(10);

            getTileCircle(possibleMove).setStroke(pawn.getPlayerColor().toColorAccent());


        }
        updateBoard();

    }

    private void clearSelection() {

        Tile[] tiles = game.getTiles();
        for (Tile t : tiles) {

            getTileCircle(t).setStrokeWidth(1);
            getTileCircle(t).setStroke(Color.BLACK);
        }
    }

    private void updateBoard() {

        for (Pawn pawn : game.getPawns()) {

            Circle pawnCirle = getPawnCircle(pawn);
            Tile tile = null;
            for (Tile t : game.getTiles()) {
                if (t.getFullId().equals(pawn.getPawnTileId())) {
                    tile = t;
                }
            }
            if (tile == null) {
                int c = 4;
            }
            pawnCirle.setCenterX(tile.getLocation().getKey());
            pawnCirle.setCenterY(tile.getLocation().getValue());
        }


        turnCircle.setFill(PlayerColor.values()[game.getCurrentPlayerId()].toColor());
        if (game.getIsDone()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            btnLeaveGame.fire();
        }
    }

    private void tilePressed(Tile tile) {

        if (game.isYourTurn() && chosenPawn != null && tile.getFullId().equals(game.getPossibleMove(chosenPawn.getPawnTileId()).getFullId())) {
            try {
                game.movePawn(chosenPawn.getFullId());
            } catch (Exception e) {
                showError(e);
            }
            updateBoard();
        }

        clearSelection();
        // imgDice.setImage(new Image(getClass().getResourceAsStream("Images/Dice0.png")));

    }

    private Color getColor(String colorName) {

        switch (colorName) {

            case "WHITE":
                return Color.WHITE;
            case "GREEN":
                return Color.GREEN;
            case "RED":
                return Color.RED;
            case "YELLOW":
                return Color.YELLOW;
            case "DODGERBLUE":
                return Color.DODGERBLUE;
            case "cyan":
                return Color.CYAN;
            case "lime":
                return Color.LIME;
            case "LIGHTGREEN":
                return Color.LIGHTGREEN;
            case "LIGHTBLUE":
                return Color.LIGHTBLUE;
            case "lemmonchiffon":
                return Color.LEMONCHIFFON;
            case "SALMON":
                return Color.SALMON;
            case "pink":
                return Color.PINK;
            case "BLUE":
                return Color.BLUE;

        }
        return Color.GRAY;
    }

    private void populatePlayingField() {


        Tile[] tiles = game.getTiles();
        Pawn[] pawns = game.getPawns();

        for (int i = 0; i < tiles.length; i++) {
            Tile tile = tiles[i];

            Circle circle = new Circle();
            circle.setCenterX(tile.getLocation().getKey());
            circle.setCenterY(tile.getLocation().getValue());
            circle.setRadius(getRadius(tile.getType()));
            circle.setFill(getColor(tile.getColor()));
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(1);
            circle.onMouseClickedProperty().set(event -> tilePressed(tile));

            tileCircles.put(tile, circle);
            boardPane.getChildren().add(circle);

            if (tile.getFullId().charAt(2) == 'P') {

                Pawn pawn = game.getPawn(tile.getFullId());
                Circle pawnCircle = new Circle(12, pawn.getPlayerColor().toColorAccent());
                pawnCircle.setCenterX(tile.getLocation().getKey());

                pawnCircle.setCenterY(tile.getLocation().getValue());
                pawnCircles.put(pawn, pawnCircle);
                boardPane.getChildren().add(pawnCircle);
                pawnCircle.onMouseClickedProperty().set(event -> pawnPressed(pawn));

            }

        }
    }

    private void addAllEventHandlers() {
        imgDice.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            throwDice();
            event.consume();
        });

        btnThrowDice.setOnAction(event -> throwDice());

        btnLeaveGame.setOnAction(event -> {
            JavaFXSceneFactory.generateStage(new MainMenuController(), getURL("guifiles/MainMenu.fxml"), false, "Hoofdmenu").show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });


        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                game.sendMessage(messageField.getText());
                updateMessages();
            }
        });

    }

    private void throwDice() {
        if (!isThrown) {
            int thrown = -1;
            try {
                thrown = game.rollDice();
                updateDice(true);
                clearSelection();

            } catch (Exception e) {

                showError(e);
            }
        }
    }

    private void updateDice(boolean effect) {

        String z = getURL("Images/Dice0.png").toString();
        Image diceNone = new Image(z);
        //imgDice.setEffect(new ColorAdjust(((color.getHue()/360) -.5f)*2, 1, 0, 0));
        String url = "Images/Dice" + game.getDiceRolled() + ".png";
        imgDice.setImage(new Image(getURL(url).toString()));
        if (effect) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(imgDice.imageProperty(), diceNone)),
                    new KeyFrame(Duration.ZERO, event -> {

                        rotateImageView(imgDice, 1440, 2.5, 1);
                        playSound("src/main/java/client/ui/Media/DiceRollSound.mp3");

                    }),
                    new KeyFrame(Duration.seconds(2), new KeyValue(imgDice.imageProperty(), new Image(getURL(url).toString()))),
                    new KeyFrame(Duration.seconds(3.5), new KeyValue(turnCircle.fillProperty(), (PlayerColor.values()[game.getCurrentPlayerId()].toColor()))),
                    new KeyFrame(Duration.seconds(2.5), new KeyValue(turnCircle.fillProperty(), Color.WHITE)));

            timeline.play();

        } else {
            imgDice.imageProperty().set(new Image(getURL(url).toString()));
            turnCircle.fillProperty().set(PlayerColor.values()[game.getCurrentPlayerId()].toColor());
        }

    }

    private void showError(Exception e) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(e.getMessage());
        a.show();
    }


    private void playSound(String path) {
        MediaPlayer soundplayer = new MediaPlayer(new Media(new File(new File(path).getAbsolutePath()).toURI().toString()));
        soundplayer.play();
    }

    private void rotateImageView(ImageView img, int degrees, double duration, int cycleAmount) {
        RotateTransition rotation = new RotateTransition(Duration.seconds(duration), img);
        rotation.setCycleCount(cycleAmount);
        rotation.setByAngle(degrees);
        rotation.setOnFinished(event -> isThrown = false);
        isThrown = true;
        rotation.play();
    }


}
