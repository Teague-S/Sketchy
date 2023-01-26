package main;

import java.util.ArrayList;
import java.util.Stack;

import commands.AddEllipseCommand;
import commands.AddLineCommand;
import commands.AddRectangleCommand;
import commands.AddShapes;
import commands.DeleteShape;
import commands.RaiseShapeCommand;
import commands.SelectShape;
import commands.ShapeFillCommand;
import commands.ShapeLayering;
import commands.TranslateShape;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class Board {
    private Pane pane;
    private boolean drawingEnabled;
    private boolean selectEnabled;
    private ArrayList<Object> shapeList = new ArrayList<>();
    public Shape selectedShape;
    private Color selectedColor;
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
    private ArrayList<Line> lines;

    public Board(Pane pane) {
        this.pane = pane;
        this.selectedColor = Color.BLACK;
    }

    public void addRectangle() {
        this.pane.setOnMouseClicked(e -> {
            Rectangle rectangle = AddShapes.addRectangle(e, selectedColor);
            if (!drawingEnabled && !selectEnabled) {
                shapeList.add(rectangle);
                this.pane.getChildren().add(rectangle);
                undoStack.push(new AddRectangleCommand(rectangle, shapeList, pane));
                redoStack.clear();
            }
        });
    }

    public void addEllipse() {
        this.pane.setOnMouseClicked(e -> {
            Ellipse ellipse = AddShapes.addEllipse(e, selectedColor);
            if (!drawingEnabled && !selectEnabled) {
                shapeList.add(ellipse);
                this.pane.getChildren().add(ellipse);
                undoStack.push(new AddEllipseCommand(ellipse, shapeList, pane));
                redoStack.clear();
            }
        });
    }

    public void addLine() {
        this.pane.setOnMousePressed(e -> {
            if (drawingEnabled && !selectEnabled) {
                lines = new ArrayList<>();
            }
        });
        this.pane.setOnMouseDragged(e -> {
            if (drawingEnabled && !selectEnabled) {
                Line line = AddShapes.addLine(e, lines, selectedColor);
                lines.add(line);
                this.pane.getChildren().add(line);
            }
        });
        this.pane.setOnMouseReleased(e -> {
            shapeList.add(lines);
            undoStack.push(new AddLineCommand(lines, shapeList, pane));
            redoStack.clear();
        });
    }

    public void setDrawingEnabled(boolean drawable) {
        drawingEnabled = drawable;
    }

    public void setSelectEnabled(boolean selected) {
        selectEnabled = selected;
    }

    public void selectShape(double x, double y) {
        for (Object shape : shapeList) {
            if (shape instanceof Ellipse) {
                Ellipse ellipse = (Ellipse) shape;
                if (ellipse.contains(x, y)) {
                    if (selectedShape != null) {
                        selectedShape.setStroke(Color.TRANSPARENT);
                    }
                    selectedShape = SelectShape.selectShape(ellipse);
                }
            } else if (shape instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) shape;
                if (rectangle.contains(x, y)) {
                    if (selectedShape != null) {
                        selectedShape.setStroke(Color.TRANSPARENT);
                    }
                    selectedShape = SelectShape.selectShape(rectangle);
                }
            }
        }
    }

    public void setFill() {
        if (selectEnabled) {
            Color originalColor = (Color) selectedShape.getFill();
            ShapeFillCommand cmd = new ShapeFillCommand(selectedShape, originalColor, selectedColor);
            cmd.execute();
            undoStack.push(cmd);
            redoStack.clear();
        }
    }

    public void setSelectedColor(Color pickedColor) {
        this.selectedColor = pickedColor;
    }

    public void deleteShape() {
        if (selectEnabled) {
            DeleteShape.deleteShape(selectedShape, pane, shapeList);
        }
    }

    public void translateShape() {
        TranslateShape.translateShape(pane, selectEnabled, shapeList, this);
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Shape newShape) {
        selectedShape = newShape;
    }

    public void raiseShape() {
        RaiseShapeCommand cmd = new RaiseShapeCommand(pane, selectedShape);
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public void lowerShape() {
        ShapeLayering.lowerShape(pane, selectedShape);
    }

    public void undoAction() {
        if (!undoStack.empty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
            //
            //
            // IN THIS METHOD, IF THE COMMAND CLASS == LINE, RUN IT TWICE
            // actually make this so it goes when it detects
            //
            //
        }
    }

    public void redoAction() {
        if (!redoStack.empty()) {
            Command cmd = redoStack.pop();
            try {
                cmd.execute();
            } catch (Exception e) {
                undoStack.push(cmd);
                redoAction();
            }
            undoStack.push(cmd);
        }
    }
}