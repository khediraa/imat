package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * By Therese Sturesson
 * Date: 2016-02-24
 * Project: imat26
 */

public class BasketController implements Initializable, ShoppingCartListener, IObservable {

    @FXML private Button backToShopButton;
    @FXML private Button toPayButton;
    @FXML private ListView basketListView;
    @FXML private Text basketTotal;
    @FXML private ShoppingCart cartInstance;
    ObservableList<ShoppingItem> cartList = FXCollections.observableArrayList();

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.cartInstance = IMatDataHandler.getInstance().getShoppingCart();

        cartList.setAll(cartInstance.getItems());
        basketListView.setItems(cartList);

        basketTotal.setText("Totalt " + cartInstance.getTotal() + " kr");

        this.cartInstance.addShoppingCartListener(this);

        // set CartItemCell to new cell type for our list view
        basketListView.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                CartItemCell cartItemCell = new CartItemCell();
                return cartItemCell;
            }
        });


        // Button events
        backToShopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcs.firePropertyChange("to-shop", true, false);
            }
        });

        toPayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcs.firePropertyChange("to-payment", true, false);
            }
        });
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        basketTotal.setText("Totalt " + cartInstance.getTotal() + " kr");

        if(cartEvent.isAddEvent()) {
            cartList.add(cartEvent.getShoppingItem());
        } else {
            if (cartEvent.getShoppingItem().getAmount() <= 0) {
                cartList.remove(cartEvent.getShoppingItem());
            }
        }

        basketListView.refresh();
    }

    public void refreshView() {
        cartList.setAll(cartInstance.getItems());
    }

    @Override
    public void addObserver(PropertyChangeListener observer) {
        this.pcs.addPropertyChangeListener(observer);
    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {
        this.pcs.removePropertyChangeListener(observer);
    }
}
