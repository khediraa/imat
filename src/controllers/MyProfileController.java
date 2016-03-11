package controllers;

import imat.IObservable;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by carlo on 2016-02-24.
 */
public class MyProfileController implements Initializable, IObservable {
    @FXML private BorderPane myProfilePane;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField address;
    @FXML private TextField postAddress;
    @FXML private TextField postCode;
    @FXML private TextField email;
    @FXML private TextField phoneNumber;
    @FXML private TextField cardNumber;
    @FXML private TextField validMonth;
    @FXML private TextField validYear;
    @FXML private TextField verificationCode;
    @FXML private Button saveDetailsButton;
    @FXML private RadioButton visaCard;
    @FXML private RadioButton mastercardCard;
    @FXML private AnchorPane savedProfileNotice;
    private boolean noticeBeingShown = false;

    private Customer customer;
    private CreditCard creditCard;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.customer = dataHandler.getCustomer();
        this.creditCard = dataHandler.getCreditCard();

        savedProfileNotice.setOpacity(0);

        getSavedUserData(); // fetch saved data and populate forms

        saveDetailsButton.addEventHandler(ActionEvent.ACTION, event -> {
            saveUserData();
            displayNotice();
        });
    }

    private void saveUserData() {
        this.customer.setFirstName(firstName.getText());
        this.customer.setLastName(lastName.getText());
        this.customer.setAddress(address.getText());
        this.customer.setPostAddress(postAddress.getText());
        this.customer.setPostCode(postCode.getText());
        this.customer.setEmail(email.getText());
        this.customer.setPhoneNumber(phoneNumber.getText());

        creditCard.setCardNumber(cardNumber.getText());

        if (validMonth.getText().length() > 0) {
            creditCard.setValidMonth(Integer.valueOf(validMonth.getText()));
        }
        if (validYear.getText().length() > 0) {
            creditCard.setValidYear(Integer.valueOf(validYear.getText()));
        }

        if (mastercardCard.isSelected()) {
            creditCard.setCardType("Mastercard");
        }
        if (visaCard.isSelected()) {
            creditCard.setCardType("Visa");
        }

        if (verificationCode.getText().length() > 0) {
            creditCard.setVerificationCode(Integer.valueOf(verificationCode.getText()));
        }


    }

    public void displayNotice() {
        if (!noticeBeingShown) {
            PauseTransition pauseTransition;
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(300));
            FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(300));

            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);

            fadeOutTransition.setFromValue(1.0);
            fadeOutTransition.setToValue(0.0);

            pauseTransition = new PauseTransition(Duration.millis(1000));

            SequentialTransition seqT = new SequentialTransition(savedProfileNotice, fadeTransition, pauseTransition, fadeOutTransition);
            seqT.play();

            noticeBeingShown = false;
        }
    }

    public void getSavedUserData() {
        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
        address.setText(customer.getAddress());
        postAddress.setText(customer.getPostAddress());
        postCode.setText(customer.getPostCode());
        email.setText(customer.getEmail());
        phoneNumber.setText(customer.getPhoneNumber());

        cardNumber.setText(creditCard.getCardNumber());
        validMonth.setText(String.valueOf(creditCard.getValidMonth()));
        validYear.setText(String.valueOf(creditCard.getValidYear()));

        if (String.valueOf(creditCard.getVerificationCode()).length() > 0) {
            verificationCode.setText(String.valueOf(creditCard.getVerificationCode()));
        }


        if (creditCard.getCardType().equals("Mastercard")) {
            mastercardCard.setSelected(true);
        }
        if (creditCard.getCardType().equals("Visa")) {
            visaCard.setSelected(true);
        }
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
