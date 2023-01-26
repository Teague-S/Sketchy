package commands;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import main.Board;

public class TranslateShape {
    public static void translateShape(Pane pane, boolean selectEnabled,
            ArrayList<Object> shapeList, Board board) {
        pane.setOnMousePressed(e -> {
            if (selectEnabled && !e.isMetaDown()) {
                boolean shapeClicked = false;
                for (Object shape : shapeList) {
                    if (shape instanceof Ellipse) {
                        Ellipse ellipse = (Ellipse) shape;
                        if (ellipse.contains(e.getX(), e.getY())) {
                            shapeClicked = true;
                            if (board.getSelectedShape() != null) {
                                board.getSelectedShape().setStroke(Color.TRANSPARENT);
                            }
                            board.setSelectedShape(ellipse);
                            break;
                        }
                    } else if (shape instanceof Rectangle) {
                        Rectangle rectangle = (Rectangle) shape;
                        if (rectangle.contains(e.getX(), e.getY())) {
                            shapeClicked = true;
                            if (board.getSelectedShape() != null) {
                                board.getSelectedShape().setStroke(Color.TRANSPARENT);
                            }
                            board.setSelectedShape(rectangle);
                            break;
                        }
                    }
                }
                if (!shapeClicked && board.getSelectedShape() != null) {
                    board.getSelectedShape().setStroke(Color.TRANSPARENT);
                    board.setSelectedShape(null);
                }
                if (board.getSelectedShape() != null &&
                        board.getSelectedShape().contains(e.getX(), e.getY())) {
                    if (board.getSelectedShape() instanceof Rectangle) {
                        Rectangle rectangle = (Rectangle) board.getSelectedShape();
                        rectangle.setX(e.getX() - rectangle.getWidth() / 2);
                        rectangle.setY(e.getY() - rectangle.getHeight() / 2);
                    } else if (board.getSelectedShape() instanceof Ellipse) {
                        Ellipse ellipse = (Ellipse) board.getSelectedShape();
                        ellipse.setCenterX(e.getX());
                        ellipse.setCenterY(e.getY());
                    }
                }
            }
        });
        pane.setOnMouseDragged(e -> {
            if (board.getSelectedShape() != null) {
                if (e.isMetaDown()) {
                    double x = e.getX();
                    double y = e.getY();
                    double angle = Math.atan2(
                            y - board.getSelectedShape().getLayoutY()
                                    - board.getSelectedShape().getLayoutBounds().getHeight() / 2,
                            x - board.getSelectedShape().getLayoutX()
                                    - board.getSelectedShape().getLayoutBounds().getWidth() / 2)
                            * 180 / Math.PI;
                    board.getSelectedShape().setRotate(angle);
                }
                if (e.isShiftDown() && board.getSelectedShape() != null && !e.isMetaDown()) {
                    double mouseX = e.getX();
                    double mouseY = e.getY();
                    double shapeX = board.getSelectedShape().getBoundsInParent().getMinX();
                    double shapeY = board.getSelectedShape().getBoundsInParent().getMinY();
                    double newWidth = mouseX - shapeX;
                    double newHeight = mouseY - shapeY;
                    if (mouseX < shapeX) {
                        newWidth = shapeX - mouseX;
                    }
                    if (mouseY < shapeY) {
                        newHeight = shapeY - mouseY;
                    }
                    if (board.getSelectedShape() instanceof Rectangle) {
                        Rectangle rectangle = (Rectangle) board.getSelectedShape();
                        rectangle.setWidth(newWidth);
                        rectangle.setHeight(newHeight);
                    } else if (board.getSelectedShape() instanceof Ellipse) {
                        Ellipse ellipse = (Ellipse) board.getSelectedShape();
                        ellipse.setRadiusX(newWidth / 2);
                        ellipse.setRadiusY(newHeight / 2);
                    }
                }
                if (selectEnabled && !e.isMetaDown() && !e.isShiftDown()) {
                    if (board.getSelectedShape() instanceof Rectangle) {
                        Rectangle rectangle = (Rectangle) board.getSelectedShape();
                        rectangle.setX(e.getX() - rectangle.getWidth() / 2);
                        rectangle.setY(e.getY() - rectangle.getHeight() / 2);
                    } else if (board.getSelectedShape() instanceof Ellipse) {
                        Ellipse ellipse = (Ellipse) board.getSelectedShape();
                        ellipse.setCenterX(e.getX());
                        ellipse.setCenterY(e.getY());
                    }
                }
            }
        });
    }

}
