package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class RootController implements Initializable, PropertyChangeListener {
    @FXML private HeaderController headerController;
    @FXML private MainPageController mainPageController;
    @FXML private CartController cartController;
    @FXML private AnchorPane root, meat, greens, dairy, cupboard, drinks, sweets;
    @FXML private AnchorPane mainPage;
    @FXML private AnchorPane basket;
    List<AnchorPane> anchorPanes = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPanes.add(meat); anchorPanes.add(greens); anchorPanes.add(dairy);
        anchorPanes.add(cupboard); anchorPanes.add(drinks); anchorPanes.add(sweets);

        headerController.sendOtherPane(mainPage);

        greens.toFront();

        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(headerController.isFirstClick()){
                mainPageController.setWidth(newValue.doubleValue());
            }
        });

        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (headerController.isFirstClick()){
                mainPageController.setHeight(newValue.doubleValue());
            }
        });

        cartController.addObserver(this);

        //DOESN'T WORK

        //Adding the navigational eventhandler to every button
        for(AnchorPane anchorPane : anchorPanes) {

            //Adding the navigation between stackpanes
            headerController.setupMovePaneAction(anchorPaneToFront(anchorPane));
            //Moving the main-page upwards

        }
    }


    /**
     * Stacks this anchorpane on top.
     */
    private EventHandler<ActionEvent> anchorPaneToFront(AnchorPane anchorPane) {
        return event -> greens.toFront();
    }

    private EventHandler<ActionEvent> toCartView() {
        return event -> basket.toFront();
    }

    private EventHandler<ActionEvent> mainPageUpwards() {
        return event -> {
            //Todo...
        };
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        basket.toFront();
    }
}
