package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class ItemTileController implements Initializable {
    @FXML Label title, price;
    @FXML Button addProductBtn;
    @FXML ImageView image;
    @FXML TextField amountField;
    private Product product;
    private ShoppingCart shoppingCart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IMatDataHandler im = IMatDataHandler.getInstance();
        this.shoppingCart = im.getShoppingCart();
    }

    @FXML
    protected void addProductToCart() {
        double amount = Double.parseDouble(amountField.getText());
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
