package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.ShoppingCartListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * By: Therese Sturesson
 * Date: 2016-02-24
 * project: imat26
 */
public class PaymentController implements Initializable, ShoppingCartListener, IObservable {

    @FXML private Button backToBasketButton;
    @FXML private Button goToDeliveryButton;
    @FXML private AnchorPane cardPayment;
    @FXML private ToggleGroup typeOfPayment;
    @FXML private RadioButton cardChoice;
    @FXML private RadioButton billChoice;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backToBasketButton.setOnAction(event -> {
            pcs.firePropertyChange("back-to-basket", true, false);
        });

        goToDeliveryButton.setOnAction(event -> {
                pcs.firePropertyChange("to-delivery", true, false);
        });

        billChoice.setOnAction(event -> {cardPayment.setDisable(true);});
        cardChoice.setOnAction(event -> cardPayment.setDisable(false));

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
