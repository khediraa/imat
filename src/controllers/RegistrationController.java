package controllers;

import imat.IObservable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by carlo on 2016-02-24.
 */
public class RegistrationController implements Initializable, IObservable {
    @FXML private BorderPane registration;
    @FXML private TextField userName;
    @FXML private PasswordField password;
    @FXML private PasswordField passwordAgain;
    @FXML private Button backButton;
    @FXML private Button registerButton;


    private String userNameString;
    private String passwordString;
    private String passwordAgainString;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    @Override
        public void initialize(URL location, ResourceBundle resources) {

        registerButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                if (userName.textProperty() != null) {
                    userNameString = userName.textProperty().toString();
                } else if (password.textProperty() != null) {
                    passwordString = password.textProperty().toString();
                } else if (passwordAgain.textProperty() != null) {
                    passwordAgainString = passwordAgain.textProperty().toString();
                } else if (password.equals(passwordAgain)) {

                }

                pcs.firePropertyChange("to-shop", true, false);
            }
        });
    }

    public void addObserver(PropertyChangeListener observer) {
        pcs.addPropertyChangeListener(observer);
    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {
        pcs.removePropertyChangeListener(observer);
    }

}