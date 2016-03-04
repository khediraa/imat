package controllers;

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

/**
 * Created by Therese on 2016-03-01.
 */
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

    private final ToggleGroup deliveryRadioGroup = new ToggleGroup();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private String time;
    private String date;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backToPaymentButton.setOnAction(event -> {
            pcs.firePropertyChange("back-to-payment", true, false);
        });

        confirmButton.setOnAction(event -> {
            time = (String)timeCombo.getValue();
            date = pickDate.getValue().toString();
            pcs.firePropertyChange("confirm-order", true, false);
            completeOrder();
        });

        homeChoice.setToggleGroup(deliveryRadioGroup);
        shopChoice.setToggleGroup(deliveryRadioGroup);

        homeChoice.setOnAction(event -> {
            deliveryTime.setDisable(true);
            deliveryDate.setDisable(true);
        });

        shopChoice.setOnAction(event -> deliveryShop.setDisable(false));


    }

    private void toggleDeliveryPanes(boolean value){
        //TODO: Move lambda function on homeChoice here, it needs to be written twice.
    }

    private void completeOrder() {
        IMatDataHandler dataHandler = IMatDataHandler.getInstance();
        dataHandler.placeOrder(true);
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
