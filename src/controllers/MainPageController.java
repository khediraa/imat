package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tuyenngo on 2016-02-25.
 */
public class MainPageController implements Initializable {
    @FXML private AnchorPane mainPage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private EventHandler<ActionEvent> move(){
        return event -> {
            //Todo...
        };
    }

    public void setWidth(double width){
        mainPage.setPrefWidth(width);
    }

    public void setHeight(double height){
        mainPage.setPrefHeight(height);
    }

    public AnchorPane getMainPage(){
        return mainPage;
    }
}
