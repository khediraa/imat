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

                // select spinner text on foxus
                if(newValue) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            amountField.selectAll();
                        }
                    });
                }
            }
        });


        // when pressing enter while editing format the input
        amountField.addEventFilter(KeyEvent.ANY, e->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                double amount = formatAmountInput(amountField);
                amountField.setText(String.valueOf(amount));
                e.consume();
            }
        });

        amountField.setOnKeyPressed(event -> {

            double oldAmount = Double.parseDouble(amountField.getText());
            double newAmount = oldAmount + 1;

            switch (event.getCode()) {
                case UP:
                    newAmount = oldAmount + 1;
                    break;
                case DOWN:
                    newAmount = oldAmount - 1;
                    break;
            }


            amountField.setText(String.valueOf(newAmount));
        });


    }

    private double formatAmountInput(TextField editor) {
        double amount = 0;

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", dfs);

        if (Utils.isValidDouble(editor.getText())) {
            amount = Double.parseDouble(editor.getText());
        }

        String test = df.format(amount);
        amountField.setText(test);

        return amount;
    }

    private void refreshAmountField() {
        ShoppingItem matchingItem = getMatchingItemInCart();
        if (matchingItem != null) {
            amountField.setText(String.valueOf(matchingItem.getAmount()));
        } else {
            amountField.setText("0");
        }
    }

    @FXML
    protected void addProductToCart() {

        ShoppingItem matchingItem = getMatchingItemInCart();
        if(matchingItem != null) {
            matchingItem.setAmount(matchingItem.getAmount() + 1);
            this.shoppingCart.fireShoppingCartChanged(matchingItem, false);
        } else {
            this.shoppingCart.addProduct(this.product, 1);
        }

        refreshAmountField();
    }

    @FXML
    protected void removeFromCart() {
        ShoppingItem matchingItem = getMatchingItemInCart();
        if(matchingItem != null) {
            if (matchingItem.getAmount() - 1 == 0) {
                matchingItem.setAmount(0);
                this.shoppingCart.removeItem(matchingItem);
            } else {
                matchingItem.setAmount(matchingItem.getAmount() - 1);
            }
            this.shoppingCart.fireShoppingCartChanged(matchingItem, false);
        }

        refreshAmountField();
    }

    public void setProduct(Product p) {
        this.product = p;
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setUnitSuffix(String unitSuffix) {
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
