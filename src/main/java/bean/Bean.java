package bean;

import java.io.IOException;

import bean.command.CommandParser;
import bean.exception.BeanListOutOfBoundsException;
import bean.exception.InvalidSyntaxException;
import bean.exception.UnknownCommandException;
import bean.storage.MemoryHandler;
import bean.task.BeanList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/** Starts the Bean command-line task manager. */
public class Bean extends Application {
    private static final BeanList TASKS = new BeanList();
    private static final MemoryHandler MEMORY_HANDLER = new MemoryHandler("memory.txt");

    @Override
    public void start(Stage stage) {

        MEMORY_HANDLER.readMemory(TASKS);
        initGui(stage);
    }

    /** Saves the current task list when the JavaFX application is closing. */
    @Override
    public void stop() {
        MEMORY_HANDLER.writeMemory(TASKS);
    }

    private static void initGui(Stage stage) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Bean.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Bean");
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
