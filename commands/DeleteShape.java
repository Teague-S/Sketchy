package commands;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class DeleteShape {
    public static void deleteShape(Shape selectedShape, Pane pane, ArrayList<Object> shapeList) {
        shapeList.remove(selectedShape);
        pane.getChildren().remove(selectedShape);

    }
}
