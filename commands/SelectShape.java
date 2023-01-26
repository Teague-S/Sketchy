package commands;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class SelectShape {
    public static Shape selectShape(Shape shape) {
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(1);
        return shape;
    }
}
