package controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;
import utils.Utils;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ResourceBundle;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class ItemTileController implements Initializable {
    @FXML Label title, price;
    @FXML Button addProductBtn;
    @FXML Button removeProductBtn;
    @FXML ImageView image;
    @FXML TextField amountField;
    @FXML Label addUnit;
    private Product product;
    private ShoppingCart shoppingCart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IMatDataHandler im = IMatDataHandler.getInstance();
        this.shoppingCart = im.getShoppingCart();


        amountField.focusedProperty().addListener(new ChangeListener<Boolean>() {
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
                e.consume();
                amountField.getParent().requestFocus();
            }
        });
    }

    private double formatAmountInput(String amountString) {
        double amount = 0;

        amountString = amountString.replace(',', '.');

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.0", dfs);

        if (Utils.isValidDouble(amountString)) {
            amount = Double.parseDouble(amountString);
        }

        String formatedText = df.format(amount);
        amountField.setText(formatedText);

        return amount;
    }



    private void refreshAmountField() {
        ShoppingItem matchingItem = getMatchingItemInCart();
        if (matchingItem != null) {
            amountField.setText(String.valueOf(matchingItem.getAmount()));
        } else {
            amountField.setText(String.valueOf(formatAmountInput("0")));
        }
    }

    private void setProductAmount(double amount) {
        ShoppingItem matchingItem = getMatchingItemInCart();
        if(matchingItem == null && amount <= 0) return;

        if(matchingItem == null) {
            this.shoppingCart.addProduct(this.product, amount);
        } else {
            if (amount <= 0) {
                matchingItem.setAmount(0);
                this.shoppingCart.removeItem(matchingItem);
            } else {
                matchingItem.setAmount(amount);
                this.shoppingCart.fireShoppingCartChanged(matchingItem, false);
            }
        }
        refreshAmountField();
    }

    @FXML protected void addProductToCart() {
        if (getMatchingItemInCart() == null) {
            setProductAmount(1);
        } else {
            setProductAmount(getMatchingItemInCart().getAmount() + 1);
        }
    }

    @FXML protected void removeFromCart() {
        if (getMatchingItemInCart() == null) {
            return;
        } else {
            setProductAmount(getMatchingItemInCart().getAmount() - 1);
        }
    }

    public void setProduct(Product p) {
        this.product = p;
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setUnitSuffix(String unitSuffix) {
        if (unitSuffix.equals("förp")) {
            unitSuffix = "fp";
        }
        if (unitSuffix.equals("burk") && unitSuffix.equals("påse")) {
            unitSuffix = "st";
        }
        this.addUnit.setText(unitSuffix);
    }

    public void setPriceAndUnit(Double price, String unit){
        this.price.setText(price + " " + unit);
    }

    public void setImage(Image imgView){
        image.setImage(imgView);
    }

    private ShoppingItem getMatchingItemInCart() {
        for(ShoppingItem cartItem : this.shoppingCart.getItems()) {
           if(cartItem.getProduct().equals(product)) {
               return cartItem;
           }
        }
        return null;
    }
}
