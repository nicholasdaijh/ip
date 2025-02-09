package nicholas.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nicholas.ui.Nicholas;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    private Nicholas nicholas = new Nicholas();

    /**
     * Starts the JavaFX application by loading the FXML layout and setting up the scene.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setNicholas(nicholas); // Inject the Nicholas instance
            stage.setTitle("Nicholas Chatbot");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
