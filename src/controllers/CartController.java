package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.*;

import java.beans.EventHandler;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * By: Sebastian Nilsson
 * Date: 16-02-23
 * Project: imat26
 */
public class CartController implements Initializable, ShoppingCartListener, IObservable {

    @FXML ListView cartListView;
    ObservableList<ShoppingItem> cartList = FXCollections.observableArrayList();
    @FXML Text cartTotal;
    @FXML Button toCartBtn;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private ShoppingCart cartInstance;
    private EventHandler toCartView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize Shopping controllers.Cart listener
        this.cartInstance = IMatDataHandler.getInstance().getShoppingCart();

        cartListView.setItems(cartList);
        this.cartInstance.addShoppingCartListener(this);

        // set CartItemCell to new cell type for our list view
        cartListView.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                CartItemCell cartItemCell = new CartItemCell();
                return cartItemCell;
            }
        });

        toCartBtn.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcs.firePropertyChange("to-basket", null, false);
            }
        });
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.CEILING);
        double total = cartInstance.getTotal();

        cartTotal.setText("Totalt " + df.format(total) + " kr");

        if (cartEvent.getShoppingItem() == null) return;


        if(cartEvent.isAddEvent()) {
            cartList.add(cartEvent.getShoppingItem());
        } else {

            System.out.println(cartInstance.getItems().size());

            if (cartEvent.getShoppingItem().getAmount() <= 0) {
                cartList.remove(cartEvent.getShoppingItem());
            }

        }

        cartListView.refresh();
    }

    public void refreshView() {
        cartList.setAll(cartInstance.getItems());
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
