package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by carlo on 2016-02-24.
 */
public class PurchaseHistoryController implements Initializable, IObservable {
    @FXML private BorderPane purchaseHistory;
    @FXML private Accordion historyAccordion;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    ObservableList<Order> orders = FXCollections.observableArrayList();
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getOrders();
    }

    private void getOrders() {
        this.orders.setAll(dataHandler.getOrders());

        for(Order order : this.orders) {
            GridPane layout = new GridPane();

            ObservableList<ShoppingItem> itemList = FXCollections.observableArrayList();
            itemList.setAll(order.getItems());
            ListView<ShoppingItem> orderItems = new ListView<>();
            orderItems.setItems(itemList);

            orderItems.setCellFactory(new Callback<ListView<ShoppingItem>, ListCell<ShoppingItem>>() {
                @Override
                public ListCell<ShoppingItem> call(ListView<ShoppingItem> param) {
                    return new OrderHistoryCell();
                }
            });

            double amount = 0;

            for (ShoppingItem item : itemList) {
                amount += item.getAmount() * item.getProduct().getPrice();
            }

            Text total = new Text("Totalt: " + amount + " kr");

            layout.add(orderItems, 0,0);
            layout.add(total, 0,1);


            TitledPane newOrderPane = new TitledPane(order.getDate().toString(), layout);
            historyAccordion.getPanes().add(newOrderPane);
        }
    }

    public void refreshOrderHistory() {
        getOrders();
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
