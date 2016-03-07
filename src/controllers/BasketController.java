package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * By Therese Sturesson
 * Date: 2016-02-24
 * Project: imat26
 */

public class BasketController implements Initializable, ShoppingCartListener, IObservable {

    @FXML private Button backToShopButton;
    @FXML private Button toPayButton;
    @FXML private ListView basketListView;
    @FXML private Label basketTotal;
    private ShoppingCart cartInstance;
    ObservableList<ShoppingItem> cartList = FXCollections.observableArrayList();
    IMatDataHandler dataInstance = IMatDataHandler.getInstance();

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.cartInstance = IMatDataHandler.getInstance().getShoppingCart();

        cartList.setAll(cartInstance.getItems());
        basketListView.setItems(cartList);

        basketTotal.setText(cartInstance.getTotal() + " kr");

        this.cartInstance.addShoppingCartListener(this);

        // set CartItemCell to new cell type for our list view
        basketListView.setCellFactory(new Callback<ListView<ShoppingItem>, ListCell<ShoppingItem>>() {
            @Override
            public ListCell<ShoppingItem> call(ListView<ShoppingItem> param) {
                ListCell<ShoppingItem> cell = new ListCell<ShoppingItem>() {
                    @Override
                    protected void updateItem(ShoppingItem item, boolean empty){

                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {

                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/CartCell.fxml"));
                                AnchorPane cellView = loader.load();
                                CartCellController controller = loader.getController();

                                controller.setItem(item);
                                controller.setAmount(item.getAmount());
                                controller.setPrice(item.getProduct().getPrice());
                                controller.setAmount(item.getAmount());
                                controller.setProductName(item.getProduct().getName());
                                controller.setUnit(item.getProduct().getUnitSuffix());
                                controller.setProductImage(dataInstance.getFXImage(item.getProduct()));
                                cellView.prefWidthProperty().bind(basketListView.widthProperty().subtract(20));

                                setGraphic(cellView);

                            } catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                };

                return cell;
            }
        });


        // Button events
        backToShopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcs.firePropertyChange("to-shop", true, false);
            }
        });

        toPayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcs.firePropertyChange("to-payment", true, false);
            }
        });
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        basketTotal.setText("Totalt " + cartInstance.getTotal() + " kr");

        if (cartEvent.getShoppingItem() == null) return;

        if(cartEvent.isAddEvent()) {
            cartList.add(cartEvent.getShoppingItem());
        } else {

            if (cartEvent.getShoppingItem().getAmount() <= 0) {
                cartList.remove(cartEvent.getShoppingItem());
            }
        }

        basketListView.refresh();
    }

    public void refreshView() {
        cartList.setAll(cartInstance.getItems());

        checkoutCheck();
    }

    // is it ok to checkout?
    private void checkoutCheck() {
        if (cartInstance.getItems().size() < 1) {
            toPayButton.setDisable(true);
        } else {
            toPayButton.setDisable(false);
        }
    }

    @Override
    public void addObserver(PropertyChangeListener observer) {
        this.pcs.addPropertyChangeListener(observer);
    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {
        this.pcs.removePropertyChangeListener(observer);
    }
}
