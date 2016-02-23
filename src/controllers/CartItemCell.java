package controllers;

import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

/**
 * By: Sebastian Nilsson
 * Date: 16-02-23
 * Project: imat26
 */
public class CartItemCell extends ListCell<ShoppingItem> {

    ShoppingItem item;
    IMatDataHandler dataInstance = IMatDataHandler.getInstance();
    boolean addedBefore;

    public CartItemCell() {
        super();

        addedBefore = false;
    }


    @Override
    protected void updateItem(ShoppingItem item, boolean empty) {
        super.updateItem(item, empty);

        if(!empty && !addedBefore) {

            this.item = item;

            GridPane layout = new GridPane();
            Image productImage = dataInstance.getFXImage(item.getProduct(), 100, 100);
            Text test2 = new Text(item.getProduct().getName());
            Text test3 = new Text(item.getAmount() + " st");

            layout.add(new ImageView(productImage),0,0);
            layout.add(test2,1,0);
            layout.add(test3,2,0);

            setGraphic(layout);

            addedBefore = true;
        }
    }
}
