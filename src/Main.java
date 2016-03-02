import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.util.ResourceBundle;

/**
 * By: Group 26
 * Date: 16-02-17
 * Project: project26
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("iMat");
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Root.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        
        primaryStage.setOnCloseRequest(event -> {
            IMatDataHandler imatDataHandler = IMatDataHandler.getInstance();
            imatDataHandler.shutDown();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
