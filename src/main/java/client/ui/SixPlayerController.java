package client.ui;

import client.domain.classes.Pawn;
import client.domain.classes.Tile;
import client.logic.interfaces.Game;
import client.logic.localimplementation.LocalSixPlayerGame;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.HashMap;
import java.util.List;


public class SixPlayerController
{
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
    ListView chatListView;
    @FXML
    ListView playersListView;


    private Game game;
    private HashMap<Tile, Circle> tileCircles = new HashMap<>();
    private HashMap<Pawn, Circle> pawnCircles = new HashMap<>();

    @FXML
    void initialize() {
        game = new LocalSixPlayerGame();
        populatePlayingField();
        playersListView.setItems(FXCollections.observableArrayList(game.getPlayers()));
    }

    private int getRadius(String type) {
        if (type.equals("WLK")){
            return 16;
        }
        else{
            return 12;
        }
    }


    private void pawnPressed() {
        throw new UnsupportedOperationException();
    }

    private void tilePressed() {
        throw new UnsupportedOperationException();
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
            case "LIGHTYELLOW":
                return Color.LIGHTYELLOW;
            case "MAGENTA":
                return Color.MAGENTA;
            case "DARKGRAY":
                return Color.DARKGRAY;
            case "BLACK":
                return Color.BLACK;
            default:
                return Color.GRAY;
        }
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
            circle.onMouseClickedProperty().set(event -> tilePressed());

            tileCircles.put(tile, circle);
            boardPane.getChildren().add(circle);

            if (tile.getFullId().charAt(2) == 'P') {

                Pawn pawn = game.getPawn(tile.getFullId());
                Circle pawnCircle = new Circle(11, pawn.getPlayerColor().toColorAccent());
                pawnCircle.setCenterX(tile.getLocation().getKey());

                pawnCircle.setCenterY(tile.getLocation().getValue());
                pawnCircles.put(pawn, pawnCircle);
                boardPane.getChildren().add(pawnCircle);
                pawnCircle.onMouseClickedProperty().set(event -> pawnPressed());
            }

        }
    }
}
