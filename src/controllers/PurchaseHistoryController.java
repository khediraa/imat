package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

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
        this.orders.clear();
        this.orders.setAll(dataHandler.getOrders());

        Collections.reverse(this.orders);

        historyAccordion.getPanes().clear();

        for(Order order : this.orders) {
            createTitledPane(order);
        }
    }

    private void createTitledPane(Order order) {
        GridPane layout = new GridPane();

        ObservableList<ShoppingItem> itemList = FXCollections.observableArrayList();
        itemList.setAll(order.getItems());
        ListView<ShoppingItem> orderItems = new ListView<>();
        orderItems.setItems(itemList);

        ColumnConstraints col1Constraints = new ColumnConstraints();
        ColumnConstraints col2Constraints = new ColumnConstraints();

        col1Constraints.setPercentWidth(75);
        col2Constraints.setPercentWidth(25);

        layout.getColumnConstraints().add(col1Constraints);
        layout.getColumnConstraints().add(col2Constraints);

        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(5,5,5,5));

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
        layout.add(total, 1,0);


        String formatedDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(order.getDate());

        TitledPane newOrderPane = new TitledPane(formatedDateString, layout);
        historyAccordion.getPanes().add(newOrderPane);
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
