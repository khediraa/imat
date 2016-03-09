package controllers;

import imat.IObservable;
import imat.LoginSession;
import javafx.animation.PathTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.*;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class HeaderController implements Initializable, IObservable {
    @FXML AnchorPane header;
    @FXML ImageView meatImg, greensImg, dairyImg, cupboardImg, drinksImg, sweetsImg, mostBoughtImg,
    purchaseHistoryImg, myProfileImg;
    @FXML VBox meatVBox, greensVBox, dairyVBox, cupboardVBox, drinksVBox, sweetsVBox, mostBoughtVBox,
            purchaseHistoryVBox, myProfileVBox;
    @FXML Button meatBtn, greensBtn, dairyBtn, cupboardBtn, drinksBtn, sweetsBtn, mostBoughtBtn, myProfileBtn,
    purchaseHistoryBtn;
    private AnchorPane otherPane;

    private List<ImageView> images = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();
    private StringProperty selectedProperty = new SimpleStringProperty("");
    private Map<Button, VBox> mapping;

    private boolean bottomPosition = true;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Add all buttons to the buttons-list due to easier access

        buttons.add(meatBtn); buttons.add(greensBtn); buttons.add(dairyBtn);
        buttons.add(cupboardBtn); buttons.add(drinksBtn); buttons.add(sweetsBtn);
        buttons.add(mostBoughtBtn); buttons.add(purchaseHistoryBtn);

        //Add all images to the images-list due to easier access
        images.add(meatImg); images.add(greensImg); images.add(dairyImg);
        images.add(cupboardImg); images.add(drinksImg); images.add(sweetsImg);
        images.add(mostBoughtImg); images.add(purchaseHistoryImg); images.add(myProfileImg);

        // Open modal for profile btn
        myProfileBtn.addEventHandler(ActionEvent.ACTION, event -> {
            LoginSession ls = LoginSession.getInstance();
            if (!ls.isLoggedIn()) {
                pcs.firePropertyChange("login-modal-dest-my-profile", true, false);
            } else {
                pcs.firePropertyChange("to-my-profile", true, false);
                startUpAnimation();
            }
        });

        purchaseHistoryBtn.addEventHandler(ActionEvent.ACTION, event -> {
            pcs.firePropertyChange("to-purchase-history", true, false);
        });

        //Map each circle to its corresponding button
        mapping = associate(new Button[]{meatBtn, greensBtn, dairyBtn, cupboardBtn, drinksBtn, sweetsBtn,
                mostBoughtBtn, purchaseHistoryBtn, myProfileBtn}, new VBox[]{meatVBox, greensVBox, dairyVBox,
                cupboardVBox, drinksVBox, sweetsVBox, mostBoughtVBox, purchaseHistoryVBox, myProfileVBox
        } );

        mapping.keySet().forEach(button -> button.setOnAction(event -> selectedProperty.set(button.getId())));
        selectedProperty.addListener(((observable, oldID, newID) -> {
            Button oldButton = find(mapping.keySet(), oldID);
            Button newButton = find(mapping.keySet(), newID);
            if (oldButton != null){
                getVBox(oldButton).getStyleClass().remove("selected-button");
            }
            if (newButton != null){
                getVBox(newButton).getStyleClass().add("selected-button");
            }
        }));

        // Most bought products
        mostBoughtBtn.addEventHandler(ActionEvent.ACTION, event -> {
            pcs.firePropertyChange("set-category-most-bought", true, false);
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
            if (bottomPosition) {
                startUpAnimation();
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
        if (bottomPosition) {
            movePane(header, header.getWidth() / 2, 100, header.getWidth() / 2, -230, 800);
            movePane(otherPane, otherPane.getWidth() / 2, otherPane.getHeight() / 2, otherPane.getWidth() / 2, -otherPane
                    .getHeight() / 2 + 40, 800);
        }

        bottomPosition = false;
    }

    public void reverseStartUpAnimation() {
        if(!bottomPosition) {
            movePane(header, header.getWidth() / 2, -230, header.getWidth() / 2, 100, 800);
            movePane(otherPane, otherPane.getWidth() / 2, -otherPane
                    .getHeight() / 2 + 40, otherPane.getWidth() / 2, otherPane
                    .getHeight() / 2, 800);

        }

        bottomPosition = true;
    }

    public boolean isBottomPosition() {
        return this.bottomPosition;
    }

    public void removeStyleClass() {
        buttons.forEach(button -> {
            if (getVBox(button).getStyleClass().contains("selected-button")) {
                getVBox(button).getStyleClass().remove("selected-button");
            }
        });
    }

    @Override
    public void addObserver(PropertyChangeListener observer) {
        this.pcs.addPropertyChangeListener(observer);
    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {
        this.pcs.removePropertyChangeListener(observer);
    }

    /** Place our buttons into a list */
    private Map<Button, VBox> associate(Button[] buttons, VBox[] stackpanes) {
        Map<Button, VBox> map = new HashMap<Button, VBox>();
        for (int i = 0; i < buttons.length; ++i)
            map.put(buttons[i], stackpanes[i]);

        return map;
    }

    /** Finds a button with "css-ID" <code>ID</code> in the list <code>list</code>. Returns null if the there was no button with such an ID. */
    private Button find(Set<Button> buttons, String ID) {
        for (Button button : buttons)
            if (button.getId().equals(ID))
                return button;

        return null;
    }


    private VBox getVBox(Button b) {
        return mapping.get(b);
    }
}
