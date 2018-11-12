package ui;

import domain.Classes.Pawn;
import domain.Classes.Tile;
import domain.Enums.PlayerColor;
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
import javafx.scene.effect.ColorAdjust;
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
import logic.interfaces.Game;
import logicFactories.LogicFactory;

import java.io.File;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;


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
    Circle TurnCircle;

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

    @FXML
    void initialize() {
        addAllEventHandlers();
        game = LogicFactory.getLocalFourPlayerGame();
        populatePlayingField();
        playersListView.setItems(FXCollections.observableArrayList(game.getPlayers()));
    }

    private int getRadius(String type) {

        switch (type) {

            case "WLK":
                return 20;
            default:
                return 15;

        }
    }

    private Circle getPawnCircle(Pawn pawn) {
        return pawnCircles.get(pawn);
    }

    private Circle getTileCircle(Tile tile) {
        return tileCircles.get(tile);
    }

    private void pawnPressed(Pawn pawn) {

        chosenPawn = pawn;
        clearSelection();
        if (game.isYourTurn()) {
            chosenPawn = pawn;
            Circle pawnCircle = getPawnCircle(pawn);
            Tile possibleMove = game.getPossibleMove(pawn);
            if(pawn.getPlayerColor().getValue() != game.getCurrentPlayerId()){

                ShowError(new IllegalArgumentException("Not " + pawn.getPlayerColor() +"'s turn"));
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

        for (Tile t : game.getTiles()) {

            getTileCircle(t).setStrokeWidth(1);
            getTileCircle(t).setStroke(Color.BLACK);
        }
    }

    private void updateBoard() {

        for (Pawn pawn : game.getPawns()) {

            Circle pawnCirle = getPawnCircle(pawn);
            Tile tile = game.getTiles().stream().filter(t -> t.getFullId().equals(pawn.getPawnTileId())).findFirst().get();
            pawnCirle.setCenterX(tile.getLocation().getKey());
            pawnCirle.setCenterY(tile.getLocation().getValue());
        }

        TurnCircle.setFill(PlayerColor.values()[game.getCurrentPlayerId()].toColor());

    }

    private void tilePressed(Tile tile) {

        if (game.isYourTurn() && chosenPawn != null && tile == game.getPossibleMove(chosenPawn)) {

            Circle pawn = getPawnCircle(chosenPawn);
          try{
            game.movePawn(chosenPawn.getFullId());
          } catch (Exception e) {

              ShowError(e);
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


        List<Tile> tiles = game.getTiles();


        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);

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

        btnLeaveGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                JavaFXSceneFactory.generateStage(new MainMenuController(), getClass().getResource("guifiles/MainMenu.fxml"), false, "Hoofdmenu").show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });


        messageField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {

                    game.sendMessage(messageField.getText());
                    messageField.clear();
                    ObservableList<String> s = FXCollections.observableArrayList(game.getMessages());
                    chatListView.setItems(s);
                }
            }
        });

    }

    private void throwDice() {
        if (!isThrown) {
            Image diceNone = new Image(getClass().getResourceAsStream("Images/Dice0.png"));
            int thrown = -1;
            try {
                thrown = game.rollDice();

            } catch (Exception e) {

                ShowError(e);
            }
            //imgDice.setEffect(new ColorAdjust(((color.getHue()/360) -.5f)*2, 1, 0, 0));
            String url = "Images/Dice" + thrown + ".png";
            imgDice.setImage(new Image(getClass().getResourceAsStream(url)));
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(imgDice.imageProperty(), diceNone)),
                    new KeyFrame(Duration.ZERO, event -> {

                        rotateImageView(imgDice, 1440, 2.5, 1);
                        playSound("src/ui/Media/DiceRollSound.mp3");

                    }),
                    new KeyFrame(Duration.seconds(2), new KeyValue(imgDice.imageProperty(), new Image(getClass().getResourceAsStream(url)))),
                  new KeyFrame(Duration.seconds(3.5), new KeyValue(TurnCircle.fillProperty(), (PlayerColor.values()[game.getCurrentPlayerId()].toColor()))),
                new KeyFrame(Duration.seconds(2.5),  new KeyValue(TurnCircle.fillProperty(),Color.WHITE)));

                    timeline.play();

        }
        clearSelection();
    }

    private void ShowError(Exception e) {
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
        rotation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isThrown = false;
            }
        });
        isThrown = true;
        rotation.play();
    }


}
