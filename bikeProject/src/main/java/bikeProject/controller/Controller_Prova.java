package bikeProject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller_Prova {

    private Scene firstScene;

    public void setFirstScene(Scene scene) {
        firstScene = scene;
    }

    @FXML
    void openOtherScene(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(firstScene);
    }

}
