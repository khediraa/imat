package controllers;

import imat.IObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCartListener;
import se.chalmers.ait.dat215.project.ShoppingItem;
import javafx.scene.control.ListView;
import se.chalmers.ait.dat215.project.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * By: Therese Sturesson
 * Date: 2016-02-24
 * Project: imat26
 */
public class ConfirmationController implements Initializable, ShoppingCartListener, IObservable {

    @FXML private Button toMainPage;
    @FXML private Label dateConfirm;
    @FXML private Label timeConfirm;
    @FXML private Label deliveryLocationText;
    private ObservableList<ShoppingItem> lastCart = FXCollections.observableArrayList();
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    @FXML private ListView receiptList;
    private IMatDataHandler dataInstance = IMatDataHandler.getInstance();
    private List<Order> orders = new ArrayList<>();
    private ObservableList<ShoppingItem> latestItemsBought = FXCollections.observableArrayList();

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        refreshOrders();

        toMainPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcs.firePropertyChange("to-main-page", true, false);
            }
        });

        receiptList.setCellFactory(new Callback<ListView<ShoppingItem>, ListCell<ShoppingItem>>() {
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
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/CartCellReceipt.fxml"));
                                AnchorPane cellView = loader.load();
                                CartCellReceiptController controller = loader.getController();

                                controller.setItem(item);
                                controller.setAmount(item.getAmount());
                                controller.setPrice(item.getProduct().getPrice() * item.getAmount());
                                controller.setProductName(item.getProduct().getName());
                                controller.setUnit(item.getProduct().getUnitSuffix());
                                controller.setProductImage(dataInstance.getFXImage(item.getProduct()));


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
    }

    public void populateListView() {
        if (dataInstance.getOrders().size() > 0) {
            receiptList.getItems().clear();
            Order lastOrder = orders.get(orders.size() - 1);
            latestItemsBought.setAll(lastOrder.getItems());
            receiptList.setItems(latestItemsBought);
        }
    }

    public void showDeliveryTime(String time, String date, boolean isHomeDelivery){
        dateConfirm.setText(date);
        timeConfirm.setText(time);

        if (isHomeDelivery) {
            deliveryLocationText.setText("Dina varor kommer levereras till din adress den: ");
            dateConfirm.setText(date);
            timeConfirm.setText(time);
        }
        else {
            deliveryLocationText.setText("Dina varor kommer levereras till butiken inom 4 dagar. \nDu får ett sms " +
                    "när de har levererats.");
            dateConfirm.setText("");
            timeConfirm.setText("");
        }

    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {

    }

    public void refreshOrders() {
        this.orders = dataHandler.getOrders();
        populateListView();
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
