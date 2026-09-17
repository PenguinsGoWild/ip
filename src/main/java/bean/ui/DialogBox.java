package bean.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

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
        if (isTaskListResponse(text)) {
            getChildren().set(0, createTaskList(text));
        }
    }

    /** Returns whether the response should be rendered as a structured task list. */
    private boolean isTaskListResponse(String text) {
        return text.startsWith("Here are the tasks in your list:");
    }

    /** Creates a styled list of task rows from the command response. */
    private VBox createTaskList(String text) {
        VBox taskList = new VBox(6);
        taskList.setMaxWidth(Double.MAX_VALUE);
        taskList.getStyleClass().add("task-list");

        String[] lines = text.split("\\R");
        Label header = new Label(lines[0]);
        header.getStyleClass().add("task-list-header");
        taskList.getChildren().add(header);

        int taskCount = 0;
        for (int i = 1; i < lines.length; i++) {
            if (!lines[i].isBlank()) {
                Label taskRow = new Label(lines[i]);
                taskRow.setWrapText(true);
                taskRow.getStyleClass().add("task-list-item");
                taskRow.getStyleClass().add(getPriorityStyleClass(lines[i]));
                taskList.getChildren().add(taskRow);
                taskCount++;
            }
        }

        if (taskCount == 0) {
            Label emptyMessage = new Label("No tasks yet.");
            emptyMessage.getStyleClass().add("task-list-empty");
            taskList.getChildren().add(emptyMessage);
        }
        return taskList;
    }

    /** Returns the style class associated with a task's priority prefix. */
    private String getPriorityStyleClass(String taskText) {
        if (taskText.contains(". HIGH ")) {
            return "priority-high";
        }
        if (taskText.contains(". MEDIUM ")) {
            return "priority-medium";
        }
        return "priority-low";
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
        return wrap(dialogBox, "user-dialog", 700);
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
        return wrap(dialogBox, styleClass, -700);
    }

    /** Wraps a dialogue in a transparent node that supplies the outer shadow. */
    private static StackPane wrap(DialogBox dialogBox, String styleClass, double startX) {
        StackPane wrapper = new StackPane(dialogBox);
        wrapper.getStyleClass().add("dialog-wrapper");
        wrapper.getStyleClass().add(styleClass);
        animateIn(wrapper, startX);
        return wrapper;
    }

    /** Animates a dialogue into place from its speaker's side. */
    private static void animateIn(StackPane wrapper, double startX) {
        Duration duration = Duration.millis(220);

        FadeTransition fade = new FadeTransition(duration, wrapper);
        fade.setFromValue(0);
        fade.setToValue(1);

        double reboundX = startX > 0 ? -14 : 14;
        TranslateTransition slide = new TranslateTransition(duration, wrapper);
        slide.setFromX(startX);
        slide.setToX(reboundX);
        slide.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition settle = new TranslateTransition(Duration.millis(90), wrapper);
        settle.setFromX(reboundX);
        settle.setToX(0);
        settle.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition pop = new ScaleTransition(duration, wrapper);
        pop.setFromX(0.94);
        pop.setFromY(0.94);
        pop.setToX(1);
        pop.setToY(1);
        pop.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(
                fade,
                pop,
                new SequentialTransition(slide, settle)).play();
    }
}
