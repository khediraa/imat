package controllers;

import imat.IObservable;
import imat.PaginationButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by carlo on 2016-02-24.
 */
public class PurchaseHistoryController implements Initializable, IObservable {
    @FXML private BorderPane purchaseHistory;
    @FXML private Accordion historyAccordion;
    @FXML private HBox paginationWrap;
    private List<PaginationButton> paginationBtns = new ArrayList<>();
    private PaginationButton activePaginationBtn;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    ObservableList<Order> orders = FXCollections.observableArrayList();
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getOrders();
        addPaginationButtons();
        displayOrdersPaginated(0);

        setActivePaginationButton(paginationBtns.get(0));
    }

    private void getOrders() {
        this.orders.clear();
        this.orders.setAll(dataHandler.getOrders());
        Collections.reverse(this.orders);
    }

    private void displayOrders(ObservableList<Order> orders) {
        historyAccordion.getPanes().clear();

        for(Order order : orders) {
            TitledPane orderPane = createTitledPane(order);
            historyAccordion.getPanes().add(orderPane);
        }
    }

    private void addPaginationButtons() {

        paginationBtns.clear();

        int numberOfPaginations = (int) Math.ceil((double)this.orders.size() / 3);

        for(int i = 0; i < numberOfPaginations; i++) {
            PaginationButton btn = new PaginationButton(String.valueOf(i + 1), i);
            btn.addEventHandler(ActionEvent.ACTION, event -> {
                displayOrdersPaginated(btn.getPaginationIndex());
                setActivePaginationButton(btn);
            });
            paginationWrap.getChildren().add(btn);
            paginationBtns.add(btn);
        }
    }

    private void displayOrdersPaginated(int paginationIndex) {
        int start = 3 * paginationIndex;

        ObservableList<Order> newOrders = FXCollections.observableArrayList();

        newOrders.add(this.orders.get(start));

        if (this.orders.size() > start + 1) {
            newOrders.add(this.orders.get(start + 1));
        }
        if (this.orders.size() > start + 2) {
            newOrders.add(this.orders.get(start + 2));
        }
        displayOrders(newOrders);
    }

    private void setActivePaginationButton(PaginationButton btn) {
        if (activePaginationBtn != null) {
            activePaginationBtn.getStyleClass().remove("pagination-btn");
        }

        btn.getStyleClass().add("pagination-btn");
        activePaginationBtn = btn;
    }

    private TitledPane createTitledPane(Order order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/HistoryDetails.fxml"));
            Node historyDetailsNode = loader.load();
            HistoryDetailsController historyDetailsController = loader.getController();

            String dateFormated = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(order.getDate());
            TitledPane orderTitledPane = new TitledPane(dateFormated, historyDetailsNode);

            historyDetailsController.populateHistoryDetails(order);

            return orderTitledPane;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void refreshOrderHistory() {
        getOrders();
        paginationWrap.getChildren().clear();
        displayOrdersPaginated(0);
        addPaginationButtons();
        setActivePaginationButton(this.paginationBtns.get(0));
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
