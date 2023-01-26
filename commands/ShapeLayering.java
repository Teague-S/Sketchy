package commands;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class ShapeLayering {

    public static void raiseShape(Pane pane, Shape selectedShape) {
        int currentIndex = pane.getChildren().indexOf(selectedShape);
        if (currentIndex < pane.getChildren().size() - 1) {
            pane.getChildren().remove(selectedShape);
            pane.getChildren().add(currentIndex + 1, selectedShape);
        }
    }

    public static void lowerShape(Pane pane, Shape selectedShape) {
        int currentIndex = pane.getChildren().indexOf(selectedShape);
        if (currentIndex > 0) {
            pane.getChildren().remove(selectedShape);
            pane.getChildren().add(currentIndex - 1, selectedShape);
        }
    }

}
