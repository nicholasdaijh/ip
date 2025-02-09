package nicholas.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A custom JavaFX component representing a dialog box.
 * This is used to display messages from both the user and the Nicholas chatbot.
 */
public class DialogBox extends HBox {
    private Label text;
    private ImageView displayPicture;

    /**
     * Constructs a {@code DialogBox} with the given text and image.
     *
     * @param s The message text to be displayed.
     * @param i The image representing the speaker.
     * @param isUser A boolean indicating whether the dialog belongs to the user.
     */
    public DialogBox(String s, Image i, boolean isUser) {
        text = new Label(s);
        text.setWrapText(true); // Enable text wrapping
        displayPicture = new ImageView(i);

        // Apply the CSS classes for styling
        if (isUser) {
            text.getStyleClass().add("dialogbox-user-text");
        } else {
            text.getStyleClass().add("dialogbox-nicholas-text");
        }

        displayPicture.getStyleClass().add("dialogbox-image");

        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);

        this.setPadding(new Insets(10, 10, 10, 10)); // Add padding inside dialog box
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setSpacing(10);
        this.getChildren().addAll(text, displayPicture);
    }

    /**
     * Flips the dialog box so that the image appears on the left
     * instead of the right, used for messages from Nicholas.
     */
    private void flip() {
        this.setAlignment(Pos.CENTER_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    /**
     * Creates a {@code DialogBox} representing a user message.
     *
     * @param s The message text from the user.
     * @param i The user's profile image.
     * @return A {@code DialogBox} with the message aligned to the right.
     */
    public static DialogBox getUserDialog(String s, Image i) {
        return new DialogBox(s, i, true);
    }

    /**
     * Creates a {@code DialogBox} representing a message from Nicholas.
     *
     * @param s The message text from Nicholas.
     * @param i Nicholas's profile image.
     * @return A {@code DialogBox} with the message aligned to the left.
     */
    public static DialogBox getNicholasDialog(String s, Image i) {
        var db = new DialogBox(s, i, false);
        db.flip();
        return db;
    }
}
