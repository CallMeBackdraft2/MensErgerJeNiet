package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class JavaFXSceneFactory {

    private JavaFXSceneFactory() {

    }

    static Stage generateStage(Object controller, URL fxmlPath, boolean resizable, String stageTitle) {
        return getStage(controller, fxmlPath, resizable, stageTitle);
    }


    static Stage generateStage(Object controller, URL fxmlPath, boolean resizable, String stageTitle, int customHeight, int customWidth) {
        Stage result = getStage(controller, fxmlPath, resizable, stageTitle);
        if (customHeight != 0) {
            result.setHeight(customHeight);
        }
        if (customWidth != 0) {
            result.setWidth(customWidth);
        }
        return result;
    }


    private static Stage getStage(Object controller, URL fxmlPath, boolean resizable, String stageTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(fxmlPath);
            loader.setController(controller);
            Stage stage = new Stage();
            stage.setTitle("Mens Erger Je Niet - " + stageTitle);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setResizable(resizable);
            return stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Stage();
    }
}
