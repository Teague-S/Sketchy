package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;
import commands.AddEllipseCommand;
import commands.AddLineCommand;
import commands.AddRectangleCommand;
import commands.AddShapes;
import commands.DeleteShapeCommand;
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
import javafx.scene.shape.Polyline;

public class Board implements Serializable {
    private Pane pane;
    private boolean drawingEnabled;
    private boolean selectEnabled;
    private ArrayList<Shape> shapeList = new ArrayList<>();
    public Shape selectedShape;
    private Color selectedColor;
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
    Polyline polyline;

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
                polyline = new Polyline();
                polyline.getPoints().addAll(e.getX(), e.getY());
                shapeList.add(polyline);
                polyline.setStroke(selectedColor);
                this.pane.getChildren().add(polyline);
                undoStack.push(new AddLineCommand(polyline, shapeList, pane));
                redoStack.clear();
            }
        });
        this.pane.setOnMouseDragged(e -> {
            if (drawingEnabled && !selectEnabled) {
                polyline.setStroke(selectedColor);
                polyline.getPoints().addAll(e.getX(), e.getY());
            }
        });
    }

    public void setDrawingEnabled(boolean drawable) {
        drawingEnabled = drawable;
    }

    public void setSelectEnabled(boolean selected) {
        selectEnabled = selected;
    }

    public void selectShape(double x, double y) {
        // if (selectEnabled) {
        // for (Object shape : shapeList) {
        // if (shape instanceof Ellipse) {
        // Ellipse ellipse = (Ellipse) shape;
        // if (ellipse.contains(x, y)) {
        // if (selectedShape != null) {
        // selectedShape.setStroke(Color.TRANSPARENT);
        // }
        // selectedShape = SelectShape.selectShape(ellipse);
        // }
        // } else if (shape instanceof Rectangle) {
        // Rectangle rectangle = (Rectangle) shape;
        // if (rectangle.contains(x, y)) {
        // if (selectedShape != null) {
        // selectedShape.setStroke(Color.TRANSPARENT);
        // }
        // selectedShape = SelectShape.selectShape(rectangle);
        // }
        // }
        // }
        // }
        for (int i = shapeList.size() - 1; i >= 0; i--) {
            Shape shape = shapeList.get(i);
            if (shape instanceof Ellipse) {
                Ellipse ellipse = (Ellipse) shape;
                if (ellipse.contains(x, y)) {
                    if (selectedShape != null) {
                        selectedShape.setStroke(Color.TRANSPARENT);
                    }
                    selectedShape = SelectShape.selectShape(ellipse);
                    break;
                }
            } else if (shape instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) shape;
                if (rectangle.contains(x, y)) {
                    if (selectedShape != null) {
                        selectedShape.setStroke(Color.TRANSPARENT);
                    }
                    selectedShape = SelectShape.selectShape(rectangle);
                    break;
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
            DeleteShapeCommand cmd = new DeleteShapeCommand(selectedShape, shapeList, pane);
            cmd.execute();
            undoStack.push(cmd);
            redoStack.clear();
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
        }
    }

    public void redoAction() {
        if (!redoStack.empty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }

    public void saveDrawing(File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(shapeList);
        } catch (Exception e) {
            System.out.println("Error while saving file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadDrawing(File file) {
        try (FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            shapeList = (ArrayList<Shape>) ois.readObject();
            for (Shape shape : shapeList) {
                pane.getChildren().add(shape);
            }
        } catch (Exception e) {
            System.out.println("Error while loading file: " + e.getMessage());
        }
    }
}
