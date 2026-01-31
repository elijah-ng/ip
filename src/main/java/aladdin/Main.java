package aladdin;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Aladdin using FXML.
 */
public class Main extends Application {

    private Aladdin aladdin = new Aladdin("Aladdin");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setAladdin(aladdin); // inject the Aladdin instance

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
