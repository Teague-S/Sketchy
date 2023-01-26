package commands;

import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class AddShapes {
    public static Rectangle addRectangle(MouseEvent e, Color selectedColor) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(50);
        rectangle.setHeight(50);
        rectangle.setX(e.getX() - rectangle.getWidth() / 2);
        rectangle.setY(e.getY() - rectangle.getHeight() / 2);
        rectangle.setFill(selectedColor);
        return rectangle;
    }

    public static Ellipse addEllipse(MouseEvent e, Color selectedColor) {
        Ellipse ellipse = new Ellipse();
        ellipse.setCenterX(e.getX());
        ellipse.setCenterY(e.getY());
        ellipse.setRadiusX(25);
        ellipse.setRadiusY(25);
        ellipse.setFill(selectedColor);
        return ellipse;
    }

    public static Line addLine(MouseEvent e, ArrayList<Line> lines, Color selectedColor) {
        if (lines.size() == 0) {
            Line line = new Line();
            line.setStartX(e.getX());
            line.setStartY(e.getY());
            line.setEndX(e.getX());
            line.setEndY(e.getY());
            line.setStroke(selectedColor);
            return line;
        } else {
            Line line = new Line();
            line.setStartX(lines.get(lines.size() - 1).getEndX());
            line.setStartY(lines.get(lines.size() - 1).getEndY());
            line.setEndX(e.getX());
            line.setEndY(e.getY());
            line.setFill(selectedColor);
            line.setStroke(selectedColor);
            return line;
        }
    }
}
