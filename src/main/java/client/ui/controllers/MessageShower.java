package client.ui.controllers;

import javafx.scene.control.Alert;

public class MessageShower {

    public static void showError(Exception e){

        Alert a = new Alert(Alert.AlertType.ERROR);
        e.printStackTrace();
        a.setHeaderText(e.getMessage());
        a.show();

    }

    public static void showInfo(String message){

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(message);
        a.show();
    }
}
