package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.ShoppingCartListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * By: Therese Sturesson
 * Date: 2016-02-24
 * Project: imat26
 */
public class ConformationController implements Initializable, ShoppingCartListener, IObservable {

    @FXML private Button toShopButton;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toShopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcs.firePropertyChange("to-shop", true, false);
            }
        });
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {

    }

    @Override
    public void addObserver(PropertyChangeListener observer) {
        pcs.addPropertyChangeListener(observer);
    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {
        pcs.removePropertyChangeListener(observer);
    }
}
