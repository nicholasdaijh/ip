package nicholas.gui;

import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import nicholas.ui.Nicholas;


/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Nicholas nicholas;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));
    private Image nicholasImage = new Image(this.getClass().getResourceAsStream("/images/Nicholas.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getNicholasDialog("Hello! What can I do for you?", nicholasImage)
        );
        BackgroundImage background = new BackgroundImage(
                new Image(this.getClass().getResource("/images/background.jpeg").toExternalForm()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true)
        );
        // Apply the background to the dialogContainer
        dialogContainer.setBackground(new Background(background));
    }

    /** Injects the Nicholas instance */
    public void setNicholas(Nicholas n) {
        nicholas = n;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws FileNotFoundException {
        try {
            String input = userInput.getText();
            String response = nicholas.getGuiResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getNicholasDialog(response, nicholasImage)
            );
            userInput.clear();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Error: File not found.");
        }
    }
}
