package bean.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            if (dialog == null || displayPicture == null) {
                throw new IllegalStateException("DialogBox.fxml must inject both dialog controls");
            }
            if (getChildren().size() != 2) {
                throw new IllegalStateException("DialogBox.fxml must contain two child nodes");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load DialogBox.fxml", e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static StackPane getUserDialog(String text, Image img) {
        DialogBox dialogBox = new DialogBox(text, img);
        return wrap(dialogBox, "user-dialog");
    }

    public static StackPane getBeanDialog(String text, Image img) {
        var dialogBox = new DialogBox(text, img);
        dialogBox.flip();
        return wrap(dialogBox, "bean-dialog");
    }

    /** Wraps a dialogue in a transparent node that supplies the outer shadow. */
    private static StackPane wrap(DialogBox dialogBox, String styleClass) {
        StackPane wrapper = new StackPane(dialogBox);
        wrapper.getStyleClass().add("dialog-wrapper");
        wrapper.getStyleClass().add(styleClass);
        return wrapper;
    }
}
