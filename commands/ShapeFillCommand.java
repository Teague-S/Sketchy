package commands;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import main.Command;

public class ShapeFillCommand implements Command {
    private Shape shape;
    private Color originalColor;
    private Color newColor;

    public ShapeFillCommand(Shape shape, Color originalColor, Color newColor) {
        this.shape = shape;
        this.originalColor = originalColor;
        this.newColor = newColor;
    }

    public void execute() {
        shape.setFill(newColor);
    }

    public void undo() {
        shape.setFill(originalColor);
    }
}
