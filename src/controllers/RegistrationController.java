package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by carlo on 2016-02-24.
 */
public class RegistrationController implements Initializable{
    @FXML private BorderPane registrationPane;
    @FXML private TextField userName;
    @FXML private PasswordField password;
    @FXML private PasswordField passwordAgain;
    @FXML private Button registerButton;

    @FXML
    public void setUserName(TextField userName) {
        this.userName = userName;
    }

    @FXML
    public void setPassword(PasswordField password) {
        this.password = password;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
