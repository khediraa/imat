package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class RootController implements Initializable{
    @FXML private HeaderController headerController;
    @FXML private AnchorPane meat, greens, dairy, cupboard, drinks, sweets;
    List<AnchorPane> anchorPanes = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPanes.add(meat); anchorPanes.add(greens); anchorPanes.add(dairy);
        anchorPanes.add(cupboard); anchorPanes.add(drinks); anchorPanes.add(sweets);

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
}
