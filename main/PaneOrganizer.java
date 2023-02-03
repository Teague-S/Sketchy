package main;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class PaneOrganizer {

    private BorderPane root;
    private Board board;
    private Stage stage;

    public PaneOrganizer(Stage stage) {
        this.stage = stage;
        this.root = new BorderPane();
        this.setupCenter();
        this.setupButtons();
    }

    private void setupCenter() {
        Pane center = new Pane();
        this.board = new Board(center);
        root.setCenter(center);
    }

    private void setupButtons() {
        VBox buttonPanel = new VBox();
        buttonPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPanel.setSpacing(10);
        buttonPanel.setMinWidth(200);
        buttonPanel.setMaxWidth(200);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setPadding(new Insets(10));
        Label shapeTitle = new Label("Drawing Options");
        shapeTitle.setPadding(new Insets(0, 0, 10, 0));
        ToggleGroup shapeGroup = new ToggleGroup();
        RadioButton selectShape = new RadioButton("Select Shape");
        selectShape.setToggleGroup(shapeGroup);
        selectShape.setOnAction(e -> {
            if (selectShape.isSelected()) {
                this.board.setSelectEnabled(true);
                this.board.translateShape();
                this.root.getCenter().setOnMouseClicked(f -> {
                    this.board.selectShape(f.getX(), f.getY());
                });
            }
        });
        RadioButton drawWithPen = new RadioButton("Draw with Pen");
        drawWithPen.setToggleGroup(shapeGroup);
        drawWithPen.setOnAction(e -> {
            if (drawWithPen.isSelected()) {
                this.board.setDrawingEnabled(true);
                this.board.setSelectEnabled(false);
                this.board.addLine();
            }
        });
        RadioButton drawRectangle = new RadioButton("Draw Rectangle");
        drawRectangle.setToggleGroup(shapeGroup);
        drawRectangle.setOnAction(e -> {
            if (drawRectangle.isSelected()) {
                this.board.setDrawingEnabled(false);
                this.board.setSelectEnabled(false);
                this.board.addRectangle();
            }
        });
        RadioButton drawEllipse = new RadioButton("Draw Ellipse");
        drawEllipse.setToggleGroup(shapeGroup);
        drawEllipse.setOnAction(e -> {
            if (drawEllipse.isSelected()) {
                this.board.setDrawingEnabled(false);
                this.board.setSelectEnabled(false);
                this.board.addEllipse();
            }
        });
        Label colorTitle = new Label("Set the Color");
        colorTitle.setPadding(new Insets(10, 0, 10, 0));
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setOnAction(e -> {
            this.board.setSelectedColor(colorPicker.getValue());
        });
        Label shapeActionsTitle = new Label("Shape Actions");
        shapeActionsTitle.setPadding(new Insets(10, 0, 10, 0));
        Button fill = new Button("Fill");
        fill.setOnAction(e -> {
            this.board.setFill();
            this.board.setSelectedColor(colorPicker.getValue());
        });
        Button delete = new Button("Delete");
        delete.setOnAction(e -> {
            this.board.deleteShape();
        });
        Button raise = new Button("Raise");
        raise.setOnAction(e -> {
            this.board.raiseShape();
        });
        Button lower = new Button("Lower");
        lower.setOnAction(e -> {
            this.board.lowerShape();
        });
        Label operationsTitle = new Label("Operations");
        operationsTitle.setPadding(new Insets(10, 0, 10, 0));
        Button undo = new Button("Undo");
        undo.setOnAction(e -> {
            this.board.undoAction();
        });
        Button redo = new Button("Redo");
        redo.setOnAction(e -> {
            this.board.redoAction();
        });
        Button save = new Button("Save");
        save.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            this.board.saveDrawing(fileChooser.showSaveDialog(this.stage));
        });
        Button load = new Button("Load");
        load.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "."),
                    new FileChooser.ExtensionFilter("JavaFX Scene Builder", "*.fxml"));
            this.board.loadDrawing(fileChooser.showOpenDialog(this.stage));
        });
        buttonPanel.getChildren().addAll(shapeTitle, selectShape, drawWithPen, drawRectangle, drawEllipse, colorTitle,
                colorPicker, shapeActionsTitle, fill, delete, raise, lower, operationsTitle, undo, redo, save, load);
        this.root.setLeft(buttonPanel);
    }

    public Pane getRoot() {
        return this.root;
    }
}