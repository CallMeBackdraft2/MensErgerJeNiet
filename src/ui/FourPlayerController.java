package ui;

import domain.Classes.Lobby;
import domain.Classes.Pawn;
import domain.Classes.Tile;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import logic.localImplementation.LocalFourPlayerGame;

import java.io.File;
import java.util.Dictionary;
import java.util.HashMap;
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
    LocalFourPlayerGame game;
    Circle selectedPawn = null;

    private Dictionary<Tile, Circle> tileCircles = new Hashtable<>();
    private Dictionary<Pawn, Circle> pawnCircles = new Hashtable<>();

    //todo check what of all this can be put into gamelogic and what has to stay tied to the UI
    private Lobby lobby;
    private boolean isThrown;
    private HashMap<String, String> identifyingList = new HashMap<String, String>() {{
        put("GR", "Green");
        put("BL", "Blue");
        put("YE", "Yellow");
        put("RE", "Red");
        put("WL", "WalkingTile");
        put("P", "Pawn");
        put("H", "HomeTile");
        put("S", "StartTile");
        put("K", "WalkingTile");
    }};

    @FXML
    void initialize() {
        addAllEventHandlers();
        game = new LocalFourPlayerGame();
        populatePlayingField();

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

    private Pawn chosenPawn;
    private void tilePressed(Tile tile) {

        if (game.isYourTurn() && chosenPawn == null) {
            Circle pawn = getPawnCircle(game.getPawn(tile.getFullId()));
            List<Tile> tiles =  game.getPossibleMoves((game.getPawn(tile.getFullId())));
            for (Tile possibleMove : tiles) {
                getTileCircle(possibleMove).setStrokeWidth(2);
                getTileCircle(possibleMove).setStroke(Color.BLACK);
            }
        }

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


            tileCircles.put(tile,circle);
            boardPane.getChildren().add(circle);

            if(tile.getFullId().charAt(2) == 'P'){

                Pawn pawn = game.getPawn(tile.getFullId());
                Circle pawnCircle = new Circle(10,Color.GRAY);
                pawnCircle.setCenterX(tile.getLocation().getKey());

                pawnCircle.setCenterY(tile.getLocation().getValue());
                pawnCircles.put(pawn,pawnCircle);
                boardPane.getChildren().add(pawnCircle);
                pawnCircle.onMouseClickedProperty().set(event -> tilePressed(tile));

            }

        }
    }

    private void addAllEventHandlers() {
        imgDice.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                throwDice();
                event.consume();
            }
        });

        btnThrowDice.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                throwDice();
            }
        });

        btnLeaveGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                JavaFXSceneFactory.generateStage(new MainMenuController(), getClass().getResource("guifiles/MainMenu.fxml"), false, "Hoofdmenu").show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });
    }

    private void throwDice() {
        if (!isThrown) {

            Image diceNone = new Image(getClass().getResourceAsStream("Images/Dice0.png"));
            int thrown = game.rollDice();
            String url = "Images/Dice" + thrown + ".png";
            imgDice.setImage(new Image(getClass().getResourceAsStream(url)));
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(imgDice.imageProperty(), diceNone)),
                    new KeyFrame(Duration.ZERO, event -> {

                        rotateImageView(imgDice, 1440, 2.5, 1);
                        playSound("src/ui/Media/DiceRollSound.mp3");

                    }),
                    new KeyFrame(Duration.seconds(2), new KeyValue(imgDice.imageProperty(), new Image(getClass().getResourceAsStream(url))))
            );
            timeline.play();
        }
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
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

    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        //todo find way to handle movement posibilities and legality
        Circle circle = (Circle) mouseEvent.getSource();
        Tile tile = lobby.getGameBoard().getPlayingField().findTileinList(Integer.parseInt(circle.getId().substring(3, 5)), identifyingList.get(circle.getId().substring(2, 3)));


        if (tile.getType().equals("Pawn")) {
            selectedPawn = circle;
        } else if (selectedPawn != null) {
            selectedPawn.setLayoutX(circle.getLayoutX());
            selectedPawn.setLayoutY(circle.getLayoutY());
        } else {

        }

    }
}
