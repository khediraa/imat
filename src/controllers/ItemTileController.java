package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class ItemTileController implements Initializable {
    @FXML Label title, price;
    @FXML ImageView image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
}
