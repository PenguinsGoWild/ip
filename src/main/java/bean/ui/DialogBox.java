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
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;
    @FXML
    private StackPane avatarContainer;
    @FXML
    private Circle imageClip;
    @FXML
    private Circle avatarBorder;

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
        dialogBox.compactUserAvatar();
        return wrap(dialogBox, "user-dialog");
    }

    /** Makes the user avatar more compact while preserving its circular border. */
    private void compactUserAvatar() {
        double avatarSize = 91;
        double imageSize = 81;
        double center = imageSize / 2;

        avatarContainer.setPrefSize(avatarSize, avatarSize);
        displayPicture.setFitWidth(imageSize);
        displayPicture.setFitHeight(imageSize);
        imageClip.setCenterX(center);
        imageClip.setCenterY(center);
        imageClip.setRadius(center);
        avatarBorder.setCenterX(center);
        avatarBorder.setCenterY(center);
        avatarBorder.setRadius(center - 1.5);
    }

    public static StackPane getBeanDialog(String text, Image img) {
        return getBeanDialog(text, img, false);
    }

    /** Creates a Bean response dialog, optionally styled as an error. */
    public static StackPane getBeanDialog(String text, Image img, boolean isError) {
        var dialogBox = new DialogBox(text, img);
        dialogBox.flip();
        String styleClass = isError ? "error-dialog" : "bean-dialog";
        return wrap(dialogBox, styleClass);
    }

    /** Wraps a dialogue in a transparent node that supplies the outer shadow. */
    private static StackPane wrap(DialogBox dialogBox, String styleClass) {
        StackPane wrapper = new StackPane(dialogBox);
        wrapper.getStyleClass().add("dialog-wrapper");
        wrapper.getStyleClass().add(styleClass);
        return wrapper;
    }
}
