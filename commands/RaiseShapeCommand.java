package commands;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import main.Command;

public class RaiseShapeCommand implements Command {
    private Pane pane;
    private Shape selectedShape;

    public RaiseShapeCommand(Pane pane, Shape selectedShape) {
        this.pane = pane;
        this.selectedShape = selectedShape;
    }

    @Override
    public void execute() {
        ShapeLayering.raiseShape(pane, selectedShape);
    }

    @Override
    public void undo() {
        ShapeLayering.lowerShape(pane, selectedShape);
    }
}
