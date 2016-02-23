package controllers;
import javafx.fxml.Initializable;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingCartListener;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * By: Sebastian Nilsson
 * Date: 16-02-23
 * Project: imat26
 */
public class CartController implements Initializable, ShoppingCartListener {

    private ShoppingCart shoppingCart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize Shopping Cart listener
        IMatDataHandler im = IMatDataHandler.getInstance();
        shoppingCart = im.getShoppingCart();
        shoppingCart.addShoppingCartListener(this);
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        System.out.println("Item added");
    }
}
