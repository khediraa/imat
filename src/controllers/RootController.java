package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class RootController implements Initializable{
    @FXML private HeaderController headerController;
    @FXML private AnchorPane root, meat, greens, dairy, cupboard, drinks, sweets;
    @FXML private Pane homePage;
    List<AnchorPane> anchorPanes = new ArrayList<>();
    Number rootWidth;
    Number rootHeight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPanes.add(meat); anchorPanes.add(greens); anchorPanes.add(dairy);
        anchorPanes.add(cupboard); anchorPanes.add(drinks); anchorPanes.add(sweets);

        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            rootWidth = newValue;
        });

        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            rootHeight = newValue;
        });

        //Adding the navigational eventhandler to every button
        for(AnchorPane anchorPane : anchorPanes) {
            headerController.addEventToButton(anchorPane.getId() + "Btn",
                    "to front", anchorPaneToFront(anchorPane));
        }
    }

    /**
     * Stacks this anchorpane on top.
     */
    private EventHandler<ActionEvent> anchorPaneToFront(AnchorPane anchorPane) {
        return event -> anchorPane.toFront();
    }

    public Number getRootWidth(){
        return rootWidth;
    }

    public Number getRootHeight(){
        return rootHeight;
    }
}
