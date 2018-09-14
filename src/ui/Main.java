package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        JavaFXSceneFactory.generateStage(getClass().getResource("guifiles/MainMenu.fxml"), false, "Hoofdmenu").show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
