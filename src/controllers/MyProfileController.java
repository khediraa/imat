package controllers;

import imat.IObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

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
    @FXML private Button toPurchaseHistoryButton;
    @FXML private Button saveDetailsButton;
    @FXML private RadioButton visaCard;
    @FXML private RadioButton matercardCard;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        toPurchaseHistoryButton.addEventHandler(ActionEvent.ACTION, event -> {
            pcs.firePropertyChange("to-registration", true, false);
        });

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
