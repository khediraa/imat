package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * By: Sebastian Nilsson
 * Date: 16-03-01
 * Project: imat26
 */
public class CartCellController implements Initializable {

    @FXML TextField amountField;
    @FXML Label unit;
    @FXML Label productName;
    @FXML Label price;
    @FXML double amount;
    @FXML Button removeItemBtn;
    ShoppingItem item;
    ShoppingCart cartInstance = IMatDataHandler.getInstance().getShoppingCart();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeItemBtn.addEventHandler(ActionEvent.ACTION, event -> {
            deleteProduct();
        });
    }

    public void setItem(ShoppingItem item) {
        this.item = item;
    }

    public void setAmount(double amount) {
        amountField.setText(String.valueOf(amount));
    }

    public void setUnit(String unitText) {
        if (unitText.equals("förp")) {
            unitText = "fp";
        }

        if (unitText.equals("burk")) {
            unitText = "fp";
        }
        this.unit.setText(unitText);
    }

    public void setProductName(String productName) {
        this.productName.setText(productName);
    }

    public void setPrice(double price) {
        this.price.setText(String.valueOf(price));
    }

    public void deleteProduct() {
        item.setAmount(0);
        cartInstance.removeItem(item);
    }

    public void updateAmount(double amount) {

    }
}
