package client.ui.controllers;

import client.domain.classes.Player;
import client.logic.scenarios.Scenario;
import client.ui.FourPlayerController;
import client.ui.JavaFXSceneFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.reflections.Reflections;
import shared.interfaces.Game;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;


public class ScenerioListController {


    @FXML
    ListView<String> listview;
    List<Class<? extends Scenario>> scenarios = new ArrayList<>();


    public void realInit() {

    }

    private URL getURL(String path){
        try {
            return new File("src/main/java/client/ui/guifiles/" + path).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    public void initialize() {

        String s = Scenario.class.getPackage().getName();
        Reflections reflections = new Reflections(s);
        Set<Class<? extends Scenario>> set = reflections.getSubTypesOf(Scenario.class);

        for (Class<? extends Scenario> aClass : set) {
            scenarios.add(aClass);
        }
        scenarios.sort(Comparator.comparing(Class::getSimpleName));

        for (Class<? extends Scenario> scenario : scenarios) {
            listview.getItems().add(scenario.getSimpleName());
        }

        listview.onMouseClickedProperty().setValue(event -> {
            int index = listview.getSelectionModel().getSelectedIndices().get(0);
            try {
                Scenario c = scenarios.get(index).newInstance();
                Game game = c.init();
                FourPlayerController controller =  new FourPlayerController(new Player(0,"test"));
                JavaFXSceneFactory.generateStage(controller,getURL( "4-Player.fxml"), false, c.getClass().getSimpleName()).show();
                controller.setGame(game);
                listview.getScene().getWindow().hide();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }
}
