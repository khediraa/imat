package controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;
import utils.Utils;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

        this.amountField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            amountField.selectAll();
                        }
                    });
                } else {
                    setProductAmount(formatAmountInput(amountField.getText()));
                }
            }
        });

        // when pressing enter while editing format the input
        amountField.addEventFilter(KeyEvent.ANY, e->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                setProductAmount(formatAmountInput(amountField.getText()));
                amountField.getParent().requestFocus();
                e.consume();
            }
        });
    }

    private double formatAmountInput(String amountString) {
        double amount = 0;
        amountString = amountString.replace(',', '.');

        if (Utils.isValidDouble(amountString)) {
            amount = Double.parseDouble(amountString);
        }

        // if this is a unit that can use decimals
        if (item.getProduct().getUnitSuffix().equals("kg") || item.getProduct().getUnitSuffix().equals("l")) {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("0.0", dfs);
            String formatedText = df.format(amount);
            amountField.setText(formatedText);
        } else {
            int intAmount = (int)Math.round(amount);
            amountField.setText(String.valueOf(intAmount));
            amount = (double)intAmount;
        }

        return amount;
    }

    private void setProductAmount(double amount) {
        ShoppingItem matchingItem = getMatchingItemInCart();
        if(matchingItem == null && amount <= 0) return;

        if(matchingItem == null) {
            this.cartInstance.addProduct(item.getProduct(), amount);
        } else {
            if (amount <= 0) {
                matchingItem.setAmount(0);
                this.cartInstance.removeItem(matchingItem);
            } else {
                matchingItem.setAmount(amount);
                this.cartInstance.fireShoppingCartChanged(matchingItem, false);
            }
        }
        refreshAmountField();
    }

    private void refreshAmountField() {
        ShoppingItem matchingItem = getMatchingItemInCart();
        if (matchingItem != null) {
            amountField.setText(Utils.getFormatedProductAmount(matchingItem.getAmount(), item.getProduct()));
        } else {
            amountField.setText(Utils.getFormatedProductAmount(0, item.getProduct()));
        }
    }

    private ShoppingItem getMatchingItemInCart() {
        for(ShoppingItem cartItem : this.cartInstance.getItems()) {
            if(cartItem.getProduct().equals(item.getProduct())) {
                return cartItem;
            }
        }
        return null;
    }

    public void setItem(ShoppingItem item) {
        this.item = item;
    }

    public void setAmount(double amount) {
        if (amount > 99) amount = 99;
        this.amount = amount;
        String formatedString = Utils.getFormatedProductAmount(amount, this.item.getProduct());
        amountField.setText(formatedString);
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
        this.price.setText(Utils.getFormatedPrice(price));
    }

    public void deleteProduct() {
        item.setAmount(0);
        cartInstance.removeItem(item);
    }

    public void updateAmount(double amount) {

    }
}
