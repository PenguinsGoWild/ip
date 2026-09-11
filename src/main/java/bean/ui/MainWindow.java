package bean.ui;

import java.util.List;

import bean.Bean;
import bean.exception.ExitCommandException;
import javafx.application.Application.Parameters;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow {
    private static final String DEFAULT_INTRO_MESSAGE = "       ▄▄▄                    \n"
            + "      ██▀▀█▄                  \n"
            + "      ██ ▄█▀             ▄    \n"
            + "      ██▀▀█▄ ▄█▀█▄ ▄▀▀█▄ ████▄\n"
            + "    ▄ ██  ▄█ ██▄█▀ ▄█▀██ ██ ██\n"
            + "    ▀██████▀▄▀█▄▄▄▄▀█▄██▄██ ▀█\n"
            + "Hello! I'm Bean.\n\nWhat can I do for you today?";
    private static final String SECRET_INTRO_MESSAGE = "     ▄▄▄▄· ▄▄▄ . ▄▄▄·  ▐ ▄ \n"
            + "     ▐█ ▀█▪▀▄.▀·▐█ ▀█ •█▌▐█\n"
            + "     ▐█▀▀█▄▐▀▀▪▄▄█▀▀█ ▐█▐▐▌\n"
            + "     ██▄▪▐█▐█▄▄▌▐█ ▪▐▌██▐█▌\n"
            + "     ·▀▀▀▀  ▀▀▀  ▀  ▀ ▀▀ █▪\n"
            + "Hello! I'm Bean.\n\nWhat can I do for you today?";
    private static final Image USER_IMAGE = new Image(
            Bean.class.getResourceAsStream("/images/BeanUser.jpg"));
    private static final Image BEAN_IMAGE = new Image(
            Bean.class.getResourceAsStream("/images/BeanBot.jpg"));

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private boolean showDefaultMessage = true;

    /** Initializes the dialog container and displays Bean's greeting. */
    @FXML
    public void initialize() {
        DottedBackground background = new DottedBackground();

        background.widthProperty().bind(rootPane.widthProperty());
        background.heightProperty().bind(rootPane.heightProperty());
        background.setMouseTransparent(true);

        rootPane.getChildren().add(0, background);

    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        try {
            addDialogs(input, Bean.getResponse(input));
        } catch (ExitCommandException e) {
            addDialogs(input, e.getMessage());
            Platform.exit();
        }
        userInput.clear();
    }

    /** Adds the user's input and Bean's response to the dialog container. */
    private void addDialogs(String input, String response) {
        if (input.isEmpty()) {
            return;
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, USER_IMAGE),
                DialogBox.getBeanDialog(response, BEAN_IMAGE));

        scrollToBottomAfterLayout();
    }

    /** Scrolls once after the scene has finished sizing the new dialogs and the scroll pane. */
    private void scrollToBottomAfterLayout() {
        Scene scene = scrollPane.getScene();
        scene.addPostLayoutPulseListener(new Runnable() {
            @Override
            public void run() {
                scene.removePostLayoutPulseListener(this);
                scrollPane.setVvalue(scrollPane.getVmax());
            }
        });
        Platform.requestNextPulse();
    }

    /** Configures the window using the parameters supplied to the application. */
    public void setParams(Parameters params) {
        List<String> rawArgs = params.getRaw();
        if (rawArgs.contains("secret")) {
            showDefaultMessage = false;
        }
    }

    /** Displays either the standard greeting or the secret greeting. */
    public void showIntroMessage() {
        String introMessage = showDefaultMessage ? DEFAULT_INTRO_MESSAGE : SECRET_INTRO_MESSAGE;
        dialogContainer.getChildren().addAll(DialogBox.getBeanDialog(introMessage, BEAN_IMAGE));
    }
}
