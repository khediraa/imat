package controllers;

import javafx.scene.control.Hyperlink;
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
public class LogInController implements Initializable {
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
    private Hyperlink forgotPasswordLink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
