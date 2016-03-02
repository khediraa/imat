package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCartListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Therese on 2016-03-01.
 */
public class DeliveryController implements Initializable, ShoppingCartListener, IObservable {

    @FXML private Button backToPaymentButton;
    @FXML private Button confirmButton;
    @FXML private GridPane deliveryMethod;
    @FXML private RadioButton shopChoice;
    @FXML private RadioButton homeChoice;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backToPaymentButton.setOnAction(event -> {
            pcs.firePropertyChange("back-to-payment", true, false);
        });

        confirmButton.setOnAction(event -> {
            pcs.firePropertyChange("confirm-order", true, false);
            completeOrder();
        });

        shopChoice.setOnAction(event -> {deliveryMethod.setDisable(true);});
        homeChoice.setOnAction(event -> deliveryMethod.setDisable(false));

    }

    private void completeOrder() {
        IMatDataHandler dataHandler = IMatDataHandler.getInstance();
        dataHandler.placeOrder(true);
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
