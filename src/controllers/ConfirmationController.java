package controllers;

import imat.IObservable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import se.chalmers.ait.dat215.project.*;

import javax.xml.soap.Text;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * By: Therese Sturesson
 * Date: 2016-02-24
 * Project: imat26
 */
public class ConfirmationController implements Initializable, ShoppingCartListener, IObservable {

    @FXML private Button toMainPage;
    @FXML private Label dateConfirm;
    @FXML private Label timeConfirm;
    @FXML private Label deliveryLocationText;
    @FXML private ListView receiptList;
    private IMatDataHandler dataInstance = IMatDataHandler.getInstance();
    private List<Order> orders = new ArrayList<>();
    private List<ShoppingItem> latestOrders = new ArrayList<>();

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toMainPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcs.firePropertyChange("to-main-page", true, false);
            }
        });

        if (dataInstance.getOrders().size() > 0) {
            orders.addAll(dataInstance.getOrders());
            Order lastOrder = orders.get(orders.size() - 1);

            lastOrder.getItems().forEach(item -> System.out.println(item.getProduct().getName()));
        }
    }

    public void showDeliveryTime(String time, String date, boolean isHomeDelivery){
        dateConfirm.setText(date);
        timeConfirm.setText(time);

        if (isHomeDelivery) {
            deliveryLocationText.setText("Dina varor kommer levereras till din adress den: ");
            dateConfirm.setText(date);
            timeConfirm.setText(time);
        }
        else {
            deliveryLocationText.setText("Dina varor kommer levereras till butiken inom 4 dagar. \nDu får ett sms " +
                    "när de har levererats.");
            dateConfirm.setText("");
            timeConfirm.setText("");
        }

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
