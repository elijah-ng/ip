package aladdin;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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
    @FXML
    private Button sendButton;

    private Aladdin aladdin;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image aladdinImage = new Image(this.getClass().getResourceAsStream("/images/DaAladdin.png"));

    /**
     * Initializes MainWindow.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        // Solution below to format GUI adapted from Claude AI
        Platform.runLater(() -> scrollPane.lookup(".viewport")
                .setStyle("-fx-background-color: transparent;"));
    }

    /** Injects the Aladdin instance. */
    public void setAladdin(Aladdin aladdin) {
        this.aladdin = aladdin;

        // Display Aladdin welcome message
        String welcomeMessage = aladdin.start();
        DialogBox dialogBox = DialogBox.getAladdinDialog(welcomeMessage, aladdinImage);
        dialogContainer.getChildren().addAll(dialogBox);
    }

    /**
     * Creates two dialog boxes, one echoing user input
     * and the other containing Aladdin's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = aladdin.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAladdinDialog(response, aladdinImage)
        );
        userInput.clear();

        // Exit on case-insensitive "bye" command
        if (input.toUpperCase().startsWith("BYE")) {
            // Solution below inspired by
            // https://stackoverflow.com/questions/30543619/how-to-use-pausetransition-method-in-javafx
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> Platform.exit());
            pause.play();
        }

    }
}
