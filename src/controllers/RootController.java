package controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.util.Duration;
import utils.Modal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by tuyenngo on 2016-02-22.
 */
public class RootController implements Initializable, PropertyChangeListener {
    @FXML private HeaderController headerController;
    @FXML private MainPageController mainPageController;
    @FXML private CartController cartController;
    @FXML private BasketController basketController;
    @FXML private PaymentController paymentController;
    @FXML private ShopController shopController;
    @FXML private LogInController logInPaneController;
    @FXML private ConformationController conformationController;
    @FXML private AnchorPane root;
    @FXML private AnchorPane mainPage;
    @FXML private AnchorPane basket;
    @FXML private AnchorPane payment;
    @FXML private AnchorPane conformation;
    @FXML private GridPane shopGrid;
    @FXML private StackPane mainContainer;
    @FXML private BorderPane logInPane;
    @FXML private BorderPane myProfilePane;
    List<AnchorPane> anchorPanes = new ArrayList<>();

    private Modal loginModal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPanes.add(root);
        headerController.sendOtherPane(mainPage);

        loginModal = new Modal(logInPane, root);
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(headerController.isFirstClick()){
                mainPageController.setWidth(newValue.doubleValue());
            }
        });

        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (headerController.isFirstClick()){
                mainPageController.setHeight(newValue.doubleValue());
            }
        });

        // Add as observer to the sub views
        cartController.addObserver(this);
        basketController.addObserver(this);
        paymentController.addObserver(this);
        conformationController.addObserver(this);
        headerController.addObserver(this);
        logInPaneController.addObserver(this);
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
                cartController.refreshView();
                break;
            case "to-payment":
                payment.toFront();
                break;
            case "back-to-basket":
                basket.toFront();
                break;
            case "confirm-order":
                conformation.toFront();
                break;
            case "set-category-meat":
                cartController.refreshView();
                shopController.displayMeat();
                shopGrid.toFront();
                break;
            case "set-category-greens":
                cartController.refreshView();
                shopController.displayGreens();
                shopGrid.toFront();
                break;
            case "set-category-dairy":
                cartController.refreshView();
                shopController.displayDairy();
                shopGrid.toFront();
                break;
            case "set-category-pantry":
                cartController.refreshView();
                shopController.displayPantry();
                shopGrid.toFront();
                break;
            case "set-category-drinks":
                cartController.refreshView();
                shopController.displayDrinks();
                shopGrid.toFront();
                break;
            case "set-category-sweets":
                cartController.refreshView();
                shopController.displaySweets();
                shopGrid.toFront();
                break;

            case "login-modal":
                loginModal.toggleModal();
                break;

            case "to-my-profile":
                myProfilePane.toFront();
                loginModal.toggleModal();
                break;
        }

    }
}
