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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.project.*;
import utils.Utils;

import javax.xml.bind.SchemaOutputResolver;
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
    private double productAmount;
    @FXML Circle removeCircle;
    private Product product;
    private ShoppingCart shoppingCart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IMatDataHandler im = IMatDataHandler.getInstance();
        this.shoppingCart = im.getShoppingCart();


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
        if (product.getUnitSuffix().equals("kg") || product.getUnitSuffix().equals("l")) {
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

    public void setAmountFormat() {
        if(this.product.getUnitSuffix().equals("kg") || this.product.getUnitSuffix().equals("l")) {
            amountField.setText("0.0");
        } else {
            amountField.setText("0");
        }
    }

    public void refreshAmountField() {
        ShoppingItem matchingItem = getMatchingItemInCart();
        if (matchingItem != null) {
            amountField.setText(Utils.getFormatedProductAmount(matchingItem.getAmount(), product));
            this.productAmount = getMatchingItemInCart().getAmount();
            refreshTile();
        } else {
            removeProductBtn.setDisable(true);
            amountField.setText(Utils.getFormatedProductAmount(0, product));
        }
    }

    private void setProductAmount(double amount) {
        if (amount > 99) amount = 99;

        ShoppingItem matchingItem = getMatchingItemInCart();
        if(matchingItem == null && amount <= 0) return;

        if(matchingItem == null) {
            this.shoppingCart.addProduct(this.product, amount);
        } else {
            if (amount <= 0) {
                matchingItem.setAmount(0);
                this.shoppingCart.removeItem(matchingItem);
                removeProductBtn.setDisable(true);
            } else {
                matchingItem.setAmount(amount);
                removeProductBtn.setDisable(false);
                this.shoppingCart.fireShoppingCartChanged(matchingItem, false);
            }
        }
        refreshAmountField();
    }

    @FXML protected void addProductToCart() {
        this.removeProductBtn.setDisable(false);
        if (getMatchingItemInCart() == null) {
            setProductAmount(1);
        } else {
            setProductAmount(getMatchingItemInCart().getAmount() + 1);
        }
    }

    @FXML protected void removeFromCart() {
        if (getMatchingItemInCart() != null && getMatchingItemInCart().getAmount() - 1 == 0) {
            this.removeProductBtn.setDisable(true);
            this.amountField.requestFocus();
            this.amountField.getParent().requestFocus();
        }
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
        if (unitSuffix.equals("burk") || unitSuffix.equals("påse")) {
            unitSuffix = "st";
        }
        this.addUnit.setText(unitSuffix);
    }

    public void setPriceAndUnit(Double price, String unit){
        this.price.setText(price + " " + unit);
    }

    public void setImage(Image imgView){
        image.setImage(imgView);
        image.setClip(new Rectangle(image.getFitWidth(), image.getFitHeight()));
    }

    private ShoppingItem getMatchingItemInCart() {
        for(ShoppingItem cartItem : this.shoppingCart.getItems()) {
           if(cartItem.getProduct().equals(product)) {
               return cartItem;
           }
        }
        return null;
    }

    public void refreshTile() {
        if (this.productAmount > 0 && this.removeProductBtn.isDisabled()) {
            this.removeProductBtn.setDisable(false);
        }
    }


}
