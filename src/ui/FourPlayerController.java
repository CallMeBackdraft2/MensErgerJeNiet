package ui;

import classes.GameBoard;
import enums.gameType;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;


public class FourPlayerController {


    private GameBoard gb = new GameBoard(gameType.FOURPLAYER);

    @FXML
    ImageView imgDice;
    @FXML
    Button btnThrowDice;
    @FXML
    Button btnLeaveGame;

    @FXML
    void initialize(){
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
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Mens Erger Je Niet");
                    stage.setScene(new Scene(root));
                    stage.show();
                    // Hide this current window (if this is what you want)
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void throwDice(){
        Image diceNone = new Image(getClass().getResourceAsStream("Images/DiceNONE.png"));
        String url = "Images/Dice" + gb.rollDice().name() + ".png";
        imgDice.setImage(new Image(getClass().getResourceAsStream(url)));

        rotateImageView(imgDice,720,2.5,1);
        playSound("src/ui/Media/DiceRollSound.mp3");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(imgDice.imageProperty(), diceNone)),
                new KeyFrame(Duration.seconds(2), new KeyValue(imgDice.imageProperty(), new Image(getClass().getResourceAsStream(url))))
        );
        timeline.play();
    }

    private void playSound(String path){
        MediaPlayer soundplayer = new MediaPlayer(new Media(new File(new File(path).getAbsolutePath()).toURI().toString()));
        soundplayer.play();
    }

    private void rotateImageView(ImageView img, int degrees, double duration, int cycleAmount){
        RotateTransition rotation = new RotateTransition(Duration.seconds(duration), img);
        rotation.setCycleCount(cycleAmount);
        rotation.setByAngle(degrees);
        rotation.play();
    }
}
