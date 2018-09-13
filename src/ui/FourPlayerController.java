package ui;

import classes.GameBoard;
import classes.Player;
import enums.gameType;
import enums.pawnState;
import enums.playerColor;
import javafx.animation.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class FourPlayerController {


    private GameBoard gb = new GameBoard(gameType.FOURPLAYER);
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
    void initialize(){
        addAllEventHandlers();
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
                JavaFXSceneFactory.generateStage(getClass().getResource("MainMenu.fxml"),false, "Hoofdmenu").show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });
    }

    private void throwDice(){
        if (!isThrown){
            Image diceNone = new Image(getClass().getResourceAsStream("Images/DiceNONE.png"));
            String url = "Images/Dice" + gb.rollDice().name() + ".png";
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
        //todo fix all this cleanly

        Circle clickedCircle = (Circle)mouseEvent.getSource();
        String circleFullID = clickedCircle.getId();
        String circleTypeID = identifyingList.get(circleFullID.substring(2,3));
        String circlePlayerID = identifyingList.get(circleFullID.substring(0,2)).toUpperCase();
        int circleNumericID = Integer.parseInt(circleFullID.substring(3,5));


        System.out.println(circleTypeID);
        System.out.println(circlePlayerID.toUpperCase());
        System.out.println(circleNumericID);
        if (circleTypeID == "Pawn"){
            System.out.println(gb.getPlayerByColor(playerColor.valueOf(circlePlayerID)));
        }




        /*System.out.println("Type: " + circleTypeID + " Player: " + circlePlayerID + " Number: " + circleNumericID);
        if(identifyingList.get(circleFullID.substring(2,3)).equals("Pawn")){
            selectedPawn = clickedCircle;
            if(player.getPawnState(Integer.parseInt(circleFullID.substring(3,5)) - 1) == pawnState.STARTPOSITION){
                player.movePawnIntoPlay(Integer.parseInt(selectedPawn.getId().substring(3,5)) - 1);
            }
        } else if(selectedPawn != null && (identifyingList.get(circleFullID.substring(0,2)).equals("WalkingTile") || identifyingList.get(circleFullID.substring(0,2)).equals(identifyingList.get(selectedPawn.getId().substring(0,2))))){
            if(player.getPawnState(Integer.parseInt(selectedPawn.getId().substring(3,5)) - 1) == pawnState.STARTPOSITION || (player.getPawnLoc(Integer.parseInt(selectedPawn.getId().substring(3,5)) - 1) < Integer.parseInt(circleFullID.substring(3,5)))){
                player.setPawnLoc(Integer.parseInt(selectedPawn.getId().substring(3,5)) - 1,Integer.parseInt(circleFullID.substring(3,5)));
                selectedPawn.setLayoutX(clickedCircle.getLayoutX());
                selectedPawn.setLayoutY(clickedCircle.getLayoutY());
            }
        }*/
    }
}
