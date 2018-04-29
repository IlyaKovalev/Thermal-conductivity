import Controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class Main extends Application{


    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Window.fxml"));
        Scene scene = new Scene(root,700,630);
        primaryStage.setScene(scene);
        scene.setOnKeyReleased(event -> {

        });
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

