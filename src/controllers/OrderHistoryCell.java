package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * By: Sebastian Nilsson
 * Date: 16-03-01
 * Project: imat26
 */
public class OrderHistoryCell extends ListCell<ShoppingItem> {

    private ShoppingItem item;


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
            ColumnConstraints col2Constraints = new ColumnConstraints();
            ColumnConstraints col3Constraints = new ColumnConstraints();

            col1Constraints.setPercentWidth(15);
            col2Constraints.setPercentWidth(60);
            col2Constraints.setPercentWidth(25);

            GridPane layout = new GridPane();

            layout.getColumnConstraints().add(col1Constraints);
            layout.getColumnConstraints().add(col2Constraints);
            layout.getColumnConstraints().add(col3Constraints);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(5,5,5,5));

            Text productName = new Text(item.getProduct().getName());
            Text amount = new Text(item.getAmount() + " " + item.getProduct().getUnitSuffix());

            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.CEILING);
            double total = item.getTotal();

            Text totalTextField = new Text();
            totalTextField.setText(String.valueOf(df.format(total)));

            layout.add(amount,0,0);
            layout.add(productName,1,0);
            layout.add(totalTextField,2,0);

            setGraphic(layout);

        }
    }
}
