package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import java.io.IOException;
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
            TitledPane orderPane = createTitledPane(order);
            historyAccordion.getPanes().add(orderPane);
        }
    }

    private TitledPane createTitledPane(Order order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/HistoryDetails.fxml"));
            Node historyDetailsNode = loader.load();
            HistoryDetailsController historyDetailsController = loader.getController();

            TitledPane orderTitledPane = new TitledPane(order.getDate().toString(), historyDetailsNode);

            historyDetailsController.populateHistoryDetails(order);

            return orderTitledPane;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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
