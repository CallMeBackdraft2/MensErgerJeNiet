package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class JavaFXSceneFactory {

    private JavaFXSceneFactory(){

    }

    static Stage generateStage(URL fxmlPath, boolean resizable, String stageTitle){
        return getStage(fxmlPath, resizable, stageTitle);
    }


    static Stage generateStage(URL fxmlPath, boolean resizable, String stageTitle, int customHeight){
        Stage result = getStage(fxmlPath,resizable, stageTitle);
        result.setHeight(customHeight);
        return result;
    }

    private static Stage getStage(URL fxmlPath, boolean resizable, String stageTitle){
        Parent root;
        try {
            root = FXMLLoader.load(fxmlPath);
            Stage stage = new Stage();
            stage.setTitle("Mens Erger Je Niet - " + stageTitle);
            stage.setScene(new Scene(root));
            stage.setResizable(resizable);
            return stage;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new Stage();
    }
}
