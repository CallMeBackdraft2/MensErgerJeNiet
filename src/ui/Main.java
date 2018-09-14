package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        JavaFXSceneFactory.generateStage(new MainMenuController(),getClass().getResource("guifiles/MainMenu.fxml"), false, "Hoofdmenu").show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
