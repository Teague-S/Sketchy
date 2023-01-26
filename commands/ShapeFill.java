package commands;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class ShapeFill {
    public static void fillShape(Shape selectedShape, Color selectedColor) {
        selectedShape.setFill(selectedColor);
    }
}
