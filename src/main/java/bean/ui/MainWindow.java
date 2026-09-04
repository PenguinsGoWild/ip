package bean.ui;

import bean.Bean;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
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

    private static Image userImage = new Image(Bean.class.getResourceAsStream("/images/BeanUser.jpg"));
    private static Image beanImage = new Image(Bean.class.getResourceAsStream("/images/BeanBot.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(DialogBox.getBeanDialog("       ▄▄▄                    \n"
                + "      ██▀▀█▄                  \n"
                + "      ██ ▄█▀             ▄    \n"
                + "      ██▀▀█▄ ▄█▀█▄ ▄▀▀█▄ ████▄\n"
                + "    ▄ ██  ▄█ ██▄█▀ ▄█▀██ ██ ██\n"
                + "    ▀██████▀▄▀█▄▄▄▄▀█▄██▄██ ▀█\n", beanImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = Bean.getCommandResult(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBeanDialog(response, beanImage)
        );
        userInput.clear();
    }

    public void exit() {
        dialogContainer.getChildren().addAll(DialogBox.getBeanDialog("Baiiii!", beanImage));
        Platform.exit();
    }
}
