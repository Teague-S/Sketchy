package commands;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import main.Command;

public class AddRectangleCommand implements Command {
    private Rectangle rectangle;
    private ArrayList<Shape> shapeList;
    private Pane pane;

    public AddRectangleCommand(Rectangle rectangle, ArrayList<Shape> shapeList, Pane pane) {
        this.rectangle = rectangle;
        this.shapeList = shapeList;
        this.pane = pane;
    }

    public void execute() {
        shapeList.add(rectangle);
        pane.getChildren().add(rectangle);
    }

    public void undo() {
        shapeList.remove(rectangle);
        pane.getChildren().remove(rectangle);
    }
}