package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.*;
import utils.Utils;

import java.beans.EventHandler;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * By: Sebastian Nilsson
 * Date: 16-02-23
 * Project: imat26
 */
public class CartController implements Initializable, ShoppingCartListener, IObservable {

    @FXML ListView cartListView;
    ObservableList<ShoppingItem> cartList = FXCollections.observableArrayList();
    @FXML Text cartTotal;
    @FXML Button toCartBtn;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private ShoppingCart cartInstance;
    private EventHandler toCartView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize Shopping controllers.Cart listener
        this.cartInstance = IMatDataHandler.getInstance().getShoppingCart();

        cartListView.setItems(cartList);
        this.cartInstance.addShoppingCartListener(this);

        refreshCart();

        // set CartItemCell to new cell type for our list view
        cartListView.setCellFactory(new Callback<ListView<ShoppingItem>, ListCell<ShoppingItem>>() {
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
                                controller.setPrice(item.getProduct().getPrice() * item.getAmount());
                                controller.setAmount(item.getAmount());
                                controller.setProductName(item.getProduct().getName());
                                controller.setUnit(item.getProduct().getUnitSuffix());
                                cellView.prefWidthProperty().bind(cartListView.widthProperty().subtract(40.0));

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

        toCartBtn.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcs.firePropertyChange("to-basket", null, false);
            }
        });
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        refreshCart();

        if (cartEvent.getShoppingItem() == null) return;

        if(cartEvent.isAddEvent()) {
            cartList.add(cartEvent.getShoppingItem());
        } else {

            if (cartEvent.getShoppingItem().getAmount() <= 0) {
                cartList.remove(cartEvent.getShoppingItem());
            }

        }

        cartListView.refresh();
    }

    private void refreshCart() {
        double total = cartInstance.getTotal();
        cartTotal.setText("Totalt " + Utils.getFormatedPrice(total) + " kr");
    }

    public void refreshView() {
        cartList.setAll(cartInstance.getItems());
    }

    @Override
    public void addObserver(PropertyChangeListener observer) {
        pcs.addPropertyChangeListener(observer);
    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {
        pcs.removePropertyChangeListener(observer);
    }
}
