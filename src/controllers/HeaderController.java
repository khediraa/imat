package controllers;

import javafx.animation.PathTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import multiEvent.MultiEvent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.*;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class HeaderController implements Initializable, IObservable {
    @FXML AnchorPane header;
    @FXML ImageView meatImg, greensImg, dairyImg, cupboardImg, drinksImg, sweetsImg;
    @FXML Button meatBtn, greensBtn, dairyBtn, cupboardBtn, drinksBtn, sweetsBtn, myProfileBtn, purchaseHistoryBtn;
    private AnchorPane otherPane;

    private List<ImageView> images = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();

    private boolean firstClick = true;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Add all buttons to the buttons-list due to easier access

        buttons.add(meatBtn); buttons.add(greensBtn); buttons.add(dairyBtn);
        buttons.add(cupboardBtn); buttons.add(drinksBtn); buttons.add(sweetsBtn);

        //Add all images to the images-list due to easier access
        images.add(meatImg); images.add(greensImg); images.add(dairyImg);
        images.add(cupboardImg); images.add(drinksImg); images.add(sweetsImg);

        // Open modal for profile btn
        myProfileBtn.addEventHandler(ActionEvent.ACTION, event -> {
            pcs.firePropertyChange("login-modal", true, false);
        });

        purchaseHistoryBtn.addEventHandler(ActionEvent.ACTION, event -> {
            pcs.firePropertyChange("to-purchase-history", true, false);
        });

        //Masking all button-images to circles
        for(ImageView image : images){
            image.setClip(new Circle(image.getFitHeight()/2, image.getFitHeight()/2,
                    image.getFitHeight()/2));
        }

        //Pairing buttons with their respective events,
        //and setting their actions.

        for(Button button : buttons){
            button.addEventHandler(ActionEvent.ACTION, pickCategory);
            button.addEventHandler(ActionEvent.ACTION, startUpAnimationClick);
        }

    }

    /**
     * Returns if first click has been clicked.
     */
    public boolean isFirstClick(){
        return firstClick;
    }

    private EventHandler<ActionEvent> pickCategory = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            Button buttonClicked = (Button)event.getSource();
            String btnId = buttonClicked.getId();

            String eventMsg = "";

            switch (btnId) {
                case "meatBtn":
                    eventMsg = "set-category-meat";
                    break;
                case "greensBtn":
                    eventMsg = "set-category-greens";
                    break;
                case "dairyBtn":
                    eventMsg = "set-category-dairy";
                    break;
                case "cupboardBtn":
                    eventMsg = "set-category-pantry";
                    break;
                case "drinksBtn":
                    eventMsg = "set-category-drinks";
                    break;
                case "sweetsBtn":
                    eventMsg = "set-category-sweets";
                    break;
                default:
                    return;
            }

            pcs.firePropertyChange(eventMsg, true, false);
        }
    };

    private EventHandler<ActionEvent> startUpAnimationClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (firstClick) {
                startUpAnimation();
                firstClick = false;
            }
        }
    };

    public void sendOtherPane(AnchorPane pane) {
        this.otherPane = pane;
    }

    private void movePane(AnchorPane pane, double fromX, double fromY, double toX, double toY, double ms) {
        Path path = new Path();
        path.getElements().add(new MoveTo(fromX, fromY));
        path.getElements().add(new LineTo(toX, toY));

        PathTransition pathTransition = new PathTransition(Duration.millis(ms),path,pane);
        pathTransition.play();
    }

    public void startUpAnimation() {
        movePane(header, header.getWidth()/2, 100, header.getWidth()/2, -230, 800);
        movePane(otherPane, otherPane.getWidth()/2, otherPane.getHeight()/2, otherPane.getWidth()/2, -otherPane
                .getHeight()/2 + 40, 800);
    }

    @Override
    public void addObserver(PropertyChangeListener observer) {
        this.pcs.addPropertyChangeListener(observer);
    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {
        this.pcs.removePropertyChangeListener(observer);
    }
}
