package ui;

import classes.Lobby;
import classes.Tile;
import javafx.animation.*;
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
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FourPlayerController {
    //todo check what of all this can be put into gamelogic and what has to stay tied to the UI
    private Lobby lobby;
    private boolean isThrown;
    private HashMap<String,String> identifyingList = new HashMap<String,String>() {{
        put("GR","Green");
        put("BL","Blue");
        put("YE","Yellow");
        put("RE","Red");
        put("WL","WalkingTile");
        put("P","Pawn");
        put("H","HomeTile");
        put("S","StartTile");
        put("K","WalkingTile");
    }};


    @FXML
    ImageView imgDice;
    @FXML
    Button btnLeaveGame;
    @FXML
    Button btnThrowDice;
    @FXML
    AnchorPane boardPane;

    @FXML
    void initialize(){
        addAllEventHandlers();
        populatePlayingField();
    }

    private void populatePlayingField() {
        for(int i = 0; i < boardPane.getChildren().size(); i++){
            if(boardPane.getChildren().get(i) instanceof Circle && boardPane.getChildren().get(i).getId() != null){
                String fullID = boardPane.getChildren().get(i).getId();
                Tile tile = null;
                if (!identifyingList.get(fullID.substring(0,2)).equals("WalkingTile")){
                    tile = new Tile(Integer.parseInt(fullID.substring(3,5)),identifyingList.get(fullID.substring(2,3)),identifyingList.get(fullID.substring(0,2)).toUpperCase());
                } else {
                    tile = new Tile(Integer.parseInt(fullID.substring(3,5)),identifyingList.get(fullID.substring(2,3)));
                }
                lobby.getGameBoard().getPlayingField().addToTileList(tile);
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
                JavaFXSceneFactory.generateStage(new MainMenuController(),getClass().getResource("guifiles/MainMenu.fxml"),false, "Hoofdmenu").show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });
    }

    private void throwDice(){
        if (!isThrown){
            Image diceNone = new Image(getClass().getResourceAsStream("Images/DiceNONE.png"));
            String url = "Images/Dice" + lobby.getGameBoard().rollDice().name() + ".png";
            imgDice.setImage(new Image(getClass().getResourceAsStream(url)));
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(imgDice.imageProperty(), diceNone)),
                    new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            rotateImageView(imgDice,720,2.5,1);
                            playSound("src/ui/Media/DiceRollSound.mp3");
                        }
                    }),
                    new KeyFrame(Duration.seconds(2), new KeyValue(imgDice.imageProperty(), new Image(getClass().getResourceAsStream(url))))
            );
            timeline.play();
        }
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    private void playSound(String path){
        MediaPlayer soundplayer = new MediaPlayer(new Media(new File(new File(path).getAbsolutePath()).toURI().toString()));
        soundplayer.play();
    }

    private void rotateImageView(ImageView img, int degrees, double duration, int cycleAmount){
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
        /*selectedPawn.setLayoutX(clickedCircle.getLayoutX());
        selectedPawn.setLayoutY(clickedCircle.getLayoutY());*/
    }
}
