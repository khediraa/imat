package controllers;

import imat.IObservable;
import imat.LoginSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import imat.Modal;
import javafx.scene.text.Text;
import sun.rmi.runtime.Log;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class RootController implements Initializable, PropertyChangeListener, IObservable {
    @FXML private HeaderController headerController;
    @FXML private MainPageController mainPageController;
    @FXML private CartController cartController;
    @FXML private BasketController basketController;
    @FXML private PaymentController paymentController;
    @FXML private ShopController shopController;
    @FXML private MyProfileController myProfileController;
    @FXML private RegistrationController registrationController;
    @FXML private ConfirmationController confirmationController;
    @FXML private LogInController logInPaneController;
    @FXML private DeliveryController deliveryController;
    @FXML private PurchaseHistoryController purchaseHistoryController;
    @FXML private Button logInButton;
    @FXML private Label logInLabel;
    @FXML private AnchorPane root;
    @FXML private AnchorPane mainPage;
    @FXML private AnchorPane cartHolder;
    @FXML private BorderPane basket;
    @FXML private BorderPane payment;
    @FXML private BorderPane confirmation;
    @FXML private GridPane shopGrid;
    @FXML private StackPane mainContainer;
    @FXML private BorderPane logInPane;
    @FXML private BorderPane myProfile;
    @FXML private BorderPane delivery;
    @FXML private BorderPane registration;
    @FXML private BorderPane purchaseHistory;
    @FXML private TextField searchBar;
    @FXML private Button searchButton;
    @FXML private Text noSearchResults;
    @FXML private Button logoBtn;
    List<AnchorPane> anchorPanes = new ArrayList<>();

    private LoginSession ls = LoginSession.getInstance();

    private Modal loginModal;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPanes.add(root);
        headerController.sendOtherPane(mainPage);

        loginModal = new Modal(logInPane, root);
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(headerController.isBottomPosition()){
                mainPageController.setWidth(newValue.doubleValue());
            }
        });

        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (headerController.isBottomPosition()){
                mainPageController.setHeight(newValue.doubleValue());
            }
        });

        //Showing log-in modal if log in button is pressed
        logInButton.setOnAction(event -> {
            if (!ls.isLoggedIn()) {
                loginModal.toggleModal();
            } else {
                ls.setLoggedIn(false);
                resetLoginName();
            }
        });

        // search bar
        // search field

        // when pressing enter while editing format the input
        searchBar.addEventFilter(KeyEvent.ANY, e->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                if (headerController.isBottomPosition()) {
                    headerController.startUpAnimation();
                }
                search();
                e.consume();
            }
        });

        searchButton.addEventFilter(ActionEvent.ACTION, event -> {
            search();
        });

        // Add as observer to the sub views
        cartController.addObserver(this);
        basketController.addObserver(this);
        paymentController.addObserver(this);
        confirmationController.addObserver(this);
        headerController.addObserver(this);
        registrationController.addObserver(this);
        logInPaneController.addObserver(this);
        deliveryController.addObserver(this);
        purchaseHistoryController.addObserver(this);
        shopController.addObserver(this);
        myProfileController.addObserver(this);


        logoBtn.addEventHandler(ActionEvent.ACTION, event -> {
            headerController.reverseStartUpAnimation();
        });
    }

    /**
     * Stacks this anchorpane on top.
     */
    private EventHandler<ActionEvent> anchorPaneToFront(AnchorPane anchorPane) {
        return event -> shopGrid.toFront();
    }

    private EventHandler<ActionEvent> toCartView() {
        return event -> basket.toFront();
    }

    private EventHandler<ActionEvent> mainPageUpwards() {
        return event -> {
            //Todo...
        };

    }

    private void search() {
        if (searchBar.getText().length() > 0) {
            shopGrid.toFront();
            shopController.search(searchBar.getText());
        }
    }

    // Button Events
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch(evt.getPropertyName()) {
            case "to-basket":
                basket.toFront();
                basketController.refreshView();
                break;

            case "to-shop":
                shopGrid.toFront();
                cartHolder.toFront();
                cartController.refreshView();
                break;

            case "to-payment":
                payment.toFront();
                break;

            case "back-to-basket":
                basket.toFront();
                break;

            case "confirm-order":
                confirmation.toFront();
                confirmationController.showDeliveryTime(
                        deliveryController.getTime(), deliveryController.getDate(), deliveryController.getIsHomeDelivery());
                break;

            case "set-category-meat":
                cartController.refreshView();
                shopController.displayMeat();
                shopGrid.toFront();
                cartHolder.toFront();
                break;

            case "set-category-greens":
                cartController.refreshView();
                shopController.displayGreens();
                shopGrid.toFront();
                cartHolder.toFront();
                break;

            case "set-category-dairy":
                cartController.refreshView();
                shopController.displayDairy();
                shopGrid.toFront();
                cartHolder.toFront();
                break;

            case "set-category-pantry":
                cartController.refreshView();
                shopController.displayPantry();
                shopGrid.toFront();
                cartHolder.toFront();
                break;

            case "set-category-drinks":
                cartController.refreshView();
                shopController.displayDrinks();
                shopGrid.toFront();
                cartHolder.toFront();
                break;

            case "set-category-sweets":
                cartController.refreshView();
                shopController.displaySweets();
                shopGrid.toFront();
                break;

            case "set-category-most-bought":
                cartController.refreshView();
                shopController.displayMostBought();
                shopGrid.toFront();
                cartHolder.toFront();
                break;

            case "login-modal":
                loginModal.openModal();
                break;

            case "login-modal-dest-my-profile":
                loginModal.setDestination("to-my-profile");
                loginModal.openModal();
                break;

            case "login-successful":
                ls.setLoggedIn(true);
                loginModal.closeModal();

                setLoginName();

                // If we have a destination connected to the modal, then let's go there!
                if (loginModal.getDestination() != null) {
                    this.propertyChange(new PropertyChangeEvent(this, loginModal.getDestination(), true, false));
                }
                break;

            case "to-my-profile":
                if (headerController.isBottomPosition()) {
                    headerController.startUpAnimation();
                }
                myProfile.toFront();
                break;

            case "to-delivery":
                delivery.toFront();
                break;

            case "back-to-payment":
                payment.toFront();
                break;

            case "to-registration":
                registration.toFront();
                loginModal.toggleModal();
                break;

            case "back-to-previous-screen":
                loginModal.toggleModal();
                break;

            case "to-purchase-history":
                cartController.refreshView();
                purchaseHistoryController.refreshOrderHistory();
                purchaseHistory.toFront();
                cartHolder.toFront();
                break;

            case "no-search-results":
                noSearchResults.toFront();
                cartHolder.toFront();
                break;
        }

    }

    public void setLoginName() {
        logInLabel.setText("Välkommen Hjördis Ohlsson!");
        logInButton.setText("Logga ut");
    }

    public void resetLoginName() {
        logInLabel.setText("Ej inloggad");
        logInButton.setText("Logga in");
    }

    @Override
    public void addObserver(PropertyChangeListener observer) {

    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {

    }
}
