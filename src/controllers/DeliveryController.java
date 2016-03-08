package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import imat.IObservable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCartListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;

public class DeliveryController implements Initializable, ShoppingCartListener, IObservable {

    @FXML private Button backToPaymentButton;
    @FXML private Button confirmButton;
    @FXML private AnchorPane deliveryShop;
    @FXML private AnchorPane deliveryDate;
    @FXML private AnchorPane deliveryTime;

    @FXML private RadioButton shopChoice;
    @FXML private RadioButton homeChoice;
    @FXML private DatePicker pickDate;
    @FXML private ComboBox timeCombo;
    @FXML private Label warningHomeText;

    private final ToggleGroup deliveryRadioGroup = new ToggleGroup();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private String time;
    private String date;
    private boolean isHomeDelivery;

    ObservableList <String> timeOptions =
            FXCollections.observableArrayList(
                    "Kl 7-10",
                    "Kl 10-13",
                    "Kl 13-16",
                    "Kl 16-19",
                    "Kl 19-22"

            );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time = "";
        date = "";
        timeCombo.setItems(timeOptions);
        warningHomeText.setVisible(false);
        confirmButton.setDisable(true);

        backToPaymentButton.setOnAction(event -> {
            pcs.firePropertyChange("back-to-payment", true, false);
        });

        confirmButton.setOnAction(event -> {
            if(isHomeDelivery && (pickDate.getValue() == null || timeCombo.getValue() == null)) {
                warningHomeText.setVisible(true);
            } else if(isHomeDelivery) {
                time = (String) timeCombo.getValue();
                date = pickDate.getValue().toString();
                pcs.firePropertyChange("confirm-order", true, false);
                completeOrder();
            } else {
                pcs.firePropertyChange("confirm-order", true, false);
                completeOrder();
            }
        });

        homeChoice.setToggleGroup(deliveryRadioGroup);
        shopChoice.setToggleGroup(deliveryRadioGroup);

        shopChoice.setOnAction(event -> toggleHomeChoice(false));
        homeChoice.setOnAction(event -> toggleHomeChoice(true));
    }

    private void toggleHomeChoice(boolean value) {
        isHomeDelivery = value;
        deliveryShop.setDisable(value);
        deliveryDate.setDisable(!value);
        deliveryTime.setDisable(!value);
        warningHomeText.setVisible(false);
        confirmButton.setDisable(false);
    }

    //Cleans up all the choices for the next order
    private void completeOrder() {
        warningHomeText.setVisible(false);
        pickDate.setValue(null);
        timeCombo.setValue(null);
        deliveryRadioGroup.selectToggle(null);
        confirmButton.setDisable(true);
        IMatDataHandler dataHandler = IMatDataHandler.getInstance();
        dataHandler.placeOrder(true);
    }

    public boolean getIsHomeDelivery () {
        return isHomeDelivery;
    }

    public String getTime(){
        return time;
    }

    public String getDate(){
        return date;
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
