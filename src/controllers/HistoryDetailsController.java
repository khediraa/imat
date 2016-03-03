package controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;
import utils.Utils;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Therese on 2016-03-02.
 */
public class HistoryDetailsController implements Initializable, IObservable{
    @FXML private BorderPane purchaseHistory;
    @FXML private TableView<TableShoppingItem> historyDetails;
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

    }

    public void populateHistoryDetails(Order order) {

        System.out.println(Utils.getOrderTotalPrice(order));
        totalPrice.setText(Utils.getFormatedPrice(Utils.getOrderTotalPrice(order)) + " kr");
        historyDetails.setEditable(false);
        historyDetails.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
            @Override
            public Boolean call(TableView.ResizeFeatures param) {
                return false;
            }
        });
        historyDetails.getColumns().setAll(amount, product, price, addToCart);

        amount.setCellValueFactory(new PropertyValueFactory<TableShoppingItem, String>("amount"));
        product.setCellValueFactory(new PropertyValueFactory<TableShoppingItem, String>("productName"));
        price.setCellValueFactory(new PropertyValueFactory<TableShoppingItem, String>("totalPrice"));
        addToCart.setCellValueFactory(new PropertyValueFactory<TableShoppingItem, Button>("addToCartButton"));

        ObservableList<TableShoppingItem> items = FXCollections.observableArrayList();

        for(ShoppingItem item : order.getItems()) {
            items.add(new TableShoppingItem(item));
        }

        historyDetails.setItems(items);


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
