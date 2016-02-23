package controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * By: Sebastian Nilsson
 * Date: 16-02-23
 * Project: imat26
 */
public class CartController implements Initializable, ShoppingCartListener {

    @FXML ListView cartListView;
    @FXML Text cartTotal;

    private ShoppingCart cartInstance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize Shopping controllers.Cart listener
        this.cartInstance = IMatDataHandler.getInstance().getShoppingCart();
        this.cartInstance.addShoppingCartListener(this);

        // set CartItemCell to new cell type for our list view
        cartListView.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                CartItemCell cartItemCell = new CartItemCell();
                //cartItemCell.addEventFilter(MouseEvent.MOUSE_CLICKED, new javafx.event.EventHandler<MouseEvent>() {

                //});
                return cartItemCell;
            }
        });
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        cartTotal.setText(cartInstance.getTotal() + " SEK");

        if(cartEvent.isAddEvent()) {
            cartListView.getItems().add(cartEvent.getShoppingItem());
        }
    }


}
