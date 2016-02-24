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
    @FXML ImageView image;
    @FXML Spinner amountField;
    @FXML Label addUnit;
    private Product product;
    private ShoppingCart shoppingCart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IMatDataHandler im = IMatDataHandler.getInstance();
        this.shoppingCart = im.getShoppingCart();

        SpinnerValueFactory.DoubleSpinnerValueFactory svf = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.00, 99.00, 1.00);

        svf.setAmountToStepBy(0.5);
        amountField.setValueFactory(svf);

        amountField.getEditor().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                // select spinner text on foxus
                if(newValue) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            amountField.getEditor().selectAll();
                        }
                    });
                }
            }
        });



        // when pressing enter while editing format the input
        amountField.getEditor().addEventFilter(KeyEvent.ANY, e->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                double amount = formatAmountInput(amountField.getEditor());
                amountField.getEditor().setText(String.valueOf(amount));
                e.consume();
            }
        });

        amountField.getEditor().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    amountField.increment(1);
                    break;
                case DOWN:
                    amountField.decrement(1);
                    break;
            }
        });


    }

    private double formatAmountInput(TextField editor) {
        double amount = 1;

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", dfs);

        if (Utils.isValidDouble(editor.getText())) {
            amount = Double.parseDouble(editor.getText());
        }

        String test = df.format(amount);
        amountField.getEditor().setText(test);

        return amount;
    }

    @FXML
    protected void addProductToCart() {

        String amountText = amountField.getEditor().getText();
        double amount = formatAmountInput(amountField.getEditor());

        ShoppingItem matchingItem = getMatchingItemInCart();
        if(matchingItem != null) {
            matchingItem.setAmount(matchingItem.getAmount() + amount);
            this.shoppingCart.fireShoppingCartChanged(matchingItem, false);
        } else {
            this.shoppingCart.addProduct(this.product, amount);
        }
    }

    public void setProduct(Product p) {
        this.product = p;
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setUnitSuffix(String unitSuffix) {
        this.addUnit.setText(unitSuffix);

        if (unitSuffix.equals("kg")) {
            amountField.getEditor().setText("1.00");
        }
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
