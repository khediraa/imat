package controllers;

import imat.IObservable;
import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;
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
public class LogInController implements Initializable, IObservable {
    @FXML
    private BorderPane logInPane;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Button logInButton;
    @FXML
    private Button toRegistrationPaneButton;
    @FXML
    private Button closeModuleButton;
    @FXML
    private Hyperlink forgotPasswordLink;

    private String userNameString;
    private String passwordString;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void change() {
        if (userName.textProperty() != null && password.textProperty() != null) {
            pcs.firePropertyChange("back-to-previous-screen", true, false);
        } else {
            //logInButton.setEnabled(false);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logInButton.addEventHandler(ActionEvent.ACTION, event -> {
            //pcs.firePropertyChange("back-to-previous-screen", true, false);
            pcs.firePropertyChange("to-my-profile", true, false);
        });

        toRegistrationPaneButton.addEventHandler(ActionEvent.ACTION, event -> {
            pcs.firePropertyChange("to-registration", true, false);
        });

        closeModuleButton.addEventHandler(ActionEvent.ACTION, event -> {
            pcs.firePropertyChange("back-to-previous-screen", true, false);
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
