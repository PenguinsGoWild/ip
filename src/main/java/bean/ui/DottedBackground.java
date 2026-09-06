package bean.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

/** Renders a resizable dotted background with a light diagonal gradient. */
public class DottedBackground extends Canvas {

    /** Creates and draws a background that redraws when its dimensions change. */
    public DottedBackground() {
        widthProperty().addListener(observable -> draw());
        heightProperty().addListener(observable -> draw());

        draw();
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();

        if (width <= 0 || height <= 0) {
            return;
        }

        GraphicsContext gc = getGraphicsContext2D();

        LinearGradient backgroundGradient = new LinearGradient(
                0, 0, 1, 1,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(215, 215, 215, 0.35)),
                new Stop(1, Color.WHITE)
        );
        gc.setFill(backgroundGradient);
        gc.fillRect(0, 0, width, height);

        double spacing = 12;
        double radius = 1.3;

        for (double y = 0; y < height; y += spacing) {
            for (double x = 0; x < width; x += spacing) {
                double progress = (x / width + y / height) / 2.0;
                double opacity = 0.20 * (1 - progress) + 0.025 * progress;

                gc.setFill(Color.rgb(70, 70, 70, opacity));
                gc.fillOval(
                        x - radius,
                        y - radius,
                        radius * 2,
                        radius * 2
                );

            }
        }
    }
}
