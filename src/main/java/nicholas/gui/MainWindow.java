package nicholas.gui;

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
 * Controller for the main GUI of the Nicholas chatbot.
 * This class handles user input and displays the chatbot's responses in a dialog format.
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

    /**
     * Initializes the GUI components, including the scroll pane,
     * chatbot greeting message, and background image.
     */
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

    /**
     * Injects the Nicholas chatbot instance into the controller.
     *
     * @param n The Nicholas chatbot instance.
     */
    public void setNicholas(Nicholas n) {
        nicholas = n;
    }

    /**
     * Handles user input by creating dialog boxes for both the user and Nicholas.
     * The user's input is displayed, and Nicholas' response is retrieved and shown.
     * The input field is cleared after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        String response = nicholas.getGuiResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getNicholasDialog(response, nicholasImage)
        );
        userInput.clear();
    }
}
