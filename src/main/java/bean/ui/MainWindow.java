package bean.ui;

import java.lang.reflect.Parameter;

import bean.Bean;
import bean.exception.ExitCommandException;
import java.util.List;
import javafx.application.Application.Parameters;
import javafx.application.Platform;
import javafx.fxml.FXML;
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

    private Parameters params;
    private boolean showDefaultMessage = true;

    /** Initializes the dialog container and displays Bean's greeting. */
    @FXML
    public void initialize() {
        DottedBackground background = new DottedBackground();

        background.widthProperty().bind(rootPane.widthProperty());
        background.heightProperty().bind(rootPane.heightProperty());
        background.setMouseTransparent(true);

        rootPane.getChildren().add(0, background);

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        

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
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, USER_IMAGE),
                DialogBox.getBeanDialog(response, BEAN_IMAGE)
        );
    }

    public void setParams(Parameters params) {
        this.params = params;
        List<String> rawArgs = params.getRaw();
        if (rawArgs.contains("secret")) {
            showDefaultMessage = false;

        }

    }

    public void showIntroMessage() {
        if (showDefaultMessage) {
            dialogContainer.getChildren().addAll(DialogBox.getBeanDialog("       ▄▄▄                    \n"
                    + "      ██▀▀█▄                  \n"
                    + "      ██ ▄█▀             ▄    \n"
                    + "      ██▀▀█▄ ▄█▀█▄ ▄▀▀█▄ ████▄\n"
                    + "    ▄ ██  ▄█ ██▄█▀ ▄█▀██ ██ ██\n"
                    + "    ▀██████▀▄▀█▄▄▄▄▀█▄██▄██ ▀█\n"
                    + "Hello! I'm Bean.\n\nWhat can I do for you today?", BEAN_IMAGE));
        } else {
            dialogContainer.getChildren().addAll(DialogBox.getBeanDialog("     ▄▄▄▄· ▄▄▄ . ▄▄▄·  ▐ ▄ \n"
                      +"     ▐█ ▀█▪▀▄.▀·▐█ ▀█ •█▌▐█\n"
                      +"     ▐█▀▀█▄▐▀▀▪▄▄█▀▀█ ▐█▐▐▌\n"
                      +"     ██▄▪▐█▐█▄▄▌▐█ ▪▐▌██▐█▌\n"
                      +"     ·▀▀▀▀  ▀▀▀  ▀  ▀ ▀▀ █▪\n"
                    + "Hello! I'm Bean.\n\nWhat can I do for you today?", BEAN_IMAGE));

        }

    }
}
