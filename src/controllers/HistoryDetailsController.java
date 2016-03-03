package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 * Created by Therese on 2016-03-02.
 */
public class HistoryDetailsController implements Initializable, IObservable{
    @FXML private BorderPane purchaseHistory;
    @FXML private TableView historyDetails;
    @FXML private TableColumn amount;
    @FXML private TableColumn product;
    @FXML private TableColumn price;
    @FXML private TableColumn addToCart;
    @FXML private Label totalPrice;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    ObservableList<Order> orders = FXCollections.observableArrayList();
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getOrders();
    }


    private void getOrders() {

        this.orders.clear();
        this.orders.setAll(dataHandler.getOrders());
    }



    private void createTitledPane(Order order) {
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

        double totalAmount = 0;

        for (ShoppingItem item : itemList) {
            totalAmount += item.getAmount() * item.getProduct().getPrice();
        }

        Text totalPrice = new Text(totalAmount + " kr");
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
