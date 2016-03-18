package controllers;

import imat.LatestOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import imat.IObservable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
<<<<<<< HEAD
=======
import javafx.util.Callback;
>>>>>>> 5acec6e35c29ef31d21cf2a92ae731e52d237d61
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCartListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.time.LocalDate;
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


    //dayCellFactory gotten from http://www.java2s.com/Tutorials/Java/JavaFX/0540__JavaFX_DatePicker.htm
    private final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
        @Override
        public DateCell call(final DatePicker datePicker) {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item.isBefore(LocalDate.now().plusDays(1))) {
                        setDisable(true);
                        setStyle("-fx-background-color: #EEEEEE;");
                    }
                }
            };
        }
    };


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time = "";
        date = "";
        timeCombo.setItems(timeOptions);
        warningHomeText.setVisible(false);

        homeChoice.setToggleGroup(deliveryRadioGroup);
        shopChoice.setToggleGroup(deliveryRadioGroup);
        isHomeDelivery = true;

        backToPaymentButton.setOnAction(event -> {
            pcs.firePropertyChange("back-to-payment", true, false);
        });

        confirmButton.setOnAction(event -> {
            if(isHomeDelivery && (pickDate.getValue() == null || timeCombo.getValue() == null)) {
                warningHomeText.setVisible(true);
            } else if(isHomeDelivery) {
                time = (String) timeCombo.getValue();
                date = pickDate.getValue().toString();
                completeOrder();
                pcs.firePropertyChange("confirm-order", true, false);
            } else {
                completeOrder();
                pcs.firePropertyChange("confirm-order", true, false);
            }
        });

        shopChoice.setOnAction(event -> toggleHomeChoice(false));
        homeChoice.setOnAction(event -> toggleHomeChoice(true));

<<<<<<< HEAD
        pickDate.setValue(LocalDate.now());
=======
        pickDate.setValue(LocalDate.now().plusDays(1));
        pickDate.setDayCellFactory(dayCellFactory);
>>>>>>> 5acec6e35c29ef31d21cf2a92ae731e52d237d61

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
        pickDate.setValue(LocalDate.now());

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
