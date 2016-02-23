package controllers;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
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

        ListView lw = getListView();
        lw.getItems();

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.item = item;

            ColumnConstraints col1Constraints = new ColumnConstraints();

            GridPane layout = new GridPane();

            layout.getColumnConstraints().add(col1Constraints);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(5,5,5,5));

            Image productImage = dataInstance.getFXImage(item.getProduct(), 100, 100);
            Text productName = new Text(item.getProduct().getName());
            Text amount = new Text(item.getAmount() + " st");

            layout.add(new ImageView(productImage),0,0);
            layout.add(productName,1,0);
            layout.add(amount,2,0);

            setGraphic(layout);

            addedBefore = true;
        }
    }

}
