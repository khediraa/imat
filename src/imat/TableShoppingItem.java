package imat;

import controllers.ItemTileController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

/**
 * By: Sebastian Nilsson
 * Date: 16-03-03
 * Project: imat26
 */
public class TableShoppingItem {

    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty productName;
    private final SimpleDoubleProperty totalPrice;
    private final Button addToCartButton;
    private ShoppingItem item;
    private ShoppingCart cartInstance = IMatDataHandler.getInstance().getShoppingCart();

    public TableShoppingItem(ShoppingItem item) {
        this.item = item;
        this.amount = new SimpleDoubleProperty(item.getAmount());
        this.productName = new SimpleStringProperty(item.getProduct().getName());
        this.totalPrice = new SimpleDoubleProperty(item.getAmount() * item.getProduct().getPrice());
        this.addToCartButton = new Button("Lägg till i varukorgen");
    }

    public double getAmount() {
        return this.amount.get();
    }

    public String getProductName() {
        return this.productName.get();
    }

    public String getTotalPrice() {
        return this.totalPrice.get() + " kr";
    }

    public Button getAddToCartButton() {
        this.addToCartButton.setOnAction(event -> {
            ShoppingItem matchingItem = getMatchingItemInCart();
            if(matchingItem != null) {
                matchingItem.setAmount(matchingItem.getAmount() + 1);
                cartInstance.fireShoppingCartChanged(matchingItem, false);
            } else {
                cartInstance.addProduct(this.item.getProduct());
            }
        });

        return this.addToCartButton;
    }

    private ShoppingItem getMatchingItemInCart() {
        for(ShoppingItem cartItem : cartInstance.getItems()) {
            if(cartItem.getProduct().equals(item.getProduct())) {
                return cartItem;
            }
        }
        return null;
    }
}
