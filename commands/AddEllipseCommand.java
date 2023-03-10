package commands;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import main.Command;

public class AddEllipseCommand implements Command {
    private Ellipse ellipse;
    private ArrayList<Shape> shapeList;
    private Pane pane;

    public AddEllipseCommand(Ellipse ellipse, ArrayList<Shape> shapeList, Pane pane) {
        this.ellipse = ellipse;
        this.shapeList = shapeList;
        this.pane = pane;
    }

    public void execute() {
        shapeList.add(ellipse);
        pane.getChildren().add(ellipse);
    }

    public void undo() {
        shapeList.remove(ellipse);
        pane.getChildren().remove(ellipse);
    }
}