package commands;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import main.Command;

public class AddLineCommand implements Command {
    Polyline polyline;
    ArrayList<Shape> shapeList;
    Pane pane;

    public AddLineCommand(Polyline polyline, ArrayList<Shape> shapeList, Pane pane) {

        this.polyline = polyline;
        this.shapeList = shapeList;
        this.pane = pane;
    }

    public void execute() {
        shapeList.add(polyline);
        pane.getChildren().add(polyline);
    }

    public void undo() {
        shapeList.remove(polyline);
        pane.getChildren().remove(polyline);
    }
}