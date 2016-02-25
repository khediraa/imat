package controllers;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import multiEvent.MultiEvent;

import java.net.URL;
import java.util.*;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class HeaderController implements Initializable{
    @FXML AnchorPane header;
    @FXML ImageView meatImg, greensImg, dairyImg, cupboardImg, drinksImg, sweetsImg;
    @FXML Button meatBtn, greensBtn, dairyBtn, cupboardBtn, drinksBtn, sweetsBtn;

    private List<ImageView> images = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();
    private Map<String,MultiEvent<ActionEvent>> buttonEvents = new HashMap<>();

    boolean firstClick = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Add all buttons to the buttons-list due to easier access
        buttons.add(meatBtn); buttons.add(greensBtn); buttons.add(dairyBtn);
        buttons.add(cupboardBtn); buttons.add(drinksBtn); buttons.add(sweetsBtn);

        //Add all images to the images-list due to easier access
        images.add(meatImg); images.add(greensImg); images.add(dairyImg);
        images.add(cupboardImg); images.add(drinksImg); images.add(sweetsImg);

        //Masking all button-images to circles
        for(ImageView image : images){
            image.setClip(new Circle(image.getFitHeight()/2, image.getFitHeight()/2,
                    image.getFitHeight()/2));
        }

        //Pairing buttons with their respective events,
        //and setting their actions.
        for(Button button : buttons){
            buttonEvents.put(button.getId(), new MultiEvent<>());
            addEventToButton(button.getId(), "move", move());
            button.setOnAction(buttonEvents.get(button.getId()));
            if(!firstClick){
                removeEventFromButton(button.getId(),"move");
            }
        }
    }


    /**
     * Adds a specified eventhandler to this button
     * @param btnName Name of this button
     * @param evtName Name of this event
     * @param event The eventhandler
     */
    public void addEventToButton(String btnName, String evtName, EventHandler<ActionEvent> event){
        if(buttonEvents.get(btnName) != null){
            buttonEvents.get(btnName).addEvent(evtName, event);
        }
    }

    public void removeEventFromButton(String btnName, String evtName){
        if(buttonEvents.containsKey(btnName)){
            buttonEvents.get(btnName).removeEvent(evtName);
        }
    }

    /**
     * Makes the header move upwards.
     */
    private EventHandler<ActionEvent> move(){
        return event -> {
            if(firstClick){
                Path path = new Path();
                path.getElements().add(new MoveTo(header.getWidth()/2, 70));
                path.getElements().add(new LineTo(header.getWidth()/2, -230));

                PathTransition pathTransition = new PathTransition(Duration.millis(800),path,header);
                pathTransition.play();
            }
            firstClick = false;
        };
    }

}
