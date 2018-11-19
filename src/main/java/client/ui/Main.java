package client.ui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        JavaFXSceneFactory.generateStage(new MainMenuController(), new File("src/main/java/client/ui/guifiles/MainMenu.fxml").toURI().toURL(), false, "Hoofdmenu").show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
