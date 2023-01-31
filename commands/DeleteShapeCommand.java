package commands;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import main.Command;

public class DeleteShapeCommand implements Command {
    private Shape shape;
    private ArrayList<Object> shapeList;
    private Pane pane;
    private int idx;

    public DeleteShapeCommand(Shape shape, ArrayList<Object> shapeList, Pane pane) {
        this.shape = shape;
        this.shapeList = shapeList;
        this.pane = pane;
    }

    @Override
    public void execute() {
        idx = shapeList.indexOf(shape);
        shapeList.remove(shape);
        pane.getChildren().remove(shape);
    }

    @Override
    public void undo() {
        shapeList.add(idx, shape);
        pane.getChildren().add(shape);
    }
}
