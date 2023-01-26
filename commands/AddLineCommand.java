package commands;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import main.Command;

public class AddLineCommand implements Command {
    ArrayList<Line> lines;
    ArrayList<Object> shapeList;
    Pane pane;

    public AddLineCommand(ArrayList<Line> lines, ArrayList<Object> shapeList, Pane pane) {

        this.lines = lines;
        this.shapeList = shapeList;
        this.pane = pane;
    }

    public void execute() {
        for (Line line : lines) {
            shapeList.add(line);
            pane.getChildren().add(line);
        }
    }

    public void undo() {
        for (Line line : lines) {
            shapeList.remove(line);
            pane.getChildren().remove(line);
        }
    }
}