package imat;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import se.chalmers.ait.dat215.project.ShoppingItem;

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

    public TableShoppingItem(ShoppingItem item) {
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

    public double getTotalPrice() {
        return this.totalPrice.get();
    }

    public Button getAddToCartButton() {
        return this.addToCartButton;
    }

}
