package bean;

import java.io.IOException;

import bean.command.CommandParser;
import bean.exception.BeanListOutOfBoundsException;
import bean.exception.InvalidSyntaxException;
import bean.exception.UnknownCommandException;
import bean.storage.MemoryHandler;
import bean.task.BeanList;
import bean.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/** Starts the Bean command-line task manager. */
public class Bean extends Application {
    private static final BeanList TASKS = new BeanList();
    private static final MemoryHandler MEMORY_HANDLER = new MemoryHandler("memory.txt");
    private static final Image WINDOW_ICON = new Image(
            Bean.class.getResourceAsStream("/images/BeanUser.jpg"));

    @Override
    public void start(Stage stage) {

        MEMORY_HANDLER.readMemory(TASKS);
        Parameters param = getParameters();

        initGui(stage, param);
    }

    /** Saves the current task list when the JavaFX application is closing. */
    @Override
    public void stop() {
        MEMORY_HANDLER.writeMemory(TASKS);
    }

    private static void initGui(Stage stage, Parameters param) {

        try {
            stage.initStyle(StageStyle.UNDECORATED);
            FXMLLoader fxmlLoader = new FXMLLoader(Bean.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            MainWindow controller = fxmlLoader.getController();
            controller.setParams(param);
            controller.showIntroMessage();
            Scene scene = new Scene(ap);
            stage.setTitle("Bean");
            stage.getIcons().add(WINDOW_ICON);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public static String getResponse(String input) {
        try {
            return CommandParser.getCommand(input, TASKS);
        } catch (UnknownCommandException e) {
            return e.getMessage();
        } catch (BeanListOutOfBoundsException e) {
            return e.getMessage();
        } catch (InvalidSyntaxException e) {
            return e.getMessage();
        }
    }
}
