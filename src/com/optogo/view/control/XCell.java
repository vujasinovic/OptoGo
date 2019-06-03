package com.optogo.view.control;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class XCell extends ListCell<String> {
    HBox hbox = new HBox();
    Label label = new Label("");
    Pane pane = new Pane();
    Label lblRemove = new Label("Remove");

    public XCell() {
        super();

        hbox.getChildren().addAll(label, pane, lblRemove);
        HBox.setHgrow(pane, Priority.ALWAYS);

        lblRemove.setTextFill(Color.DARKRED);
        lblRemove.setFont(new Font(14));
        lblRemove.setOnMouseEntered(e -> lblRemove.setStyle("-fx-underline: true; -fx-cursor: hand;"));
        lblRemove.setOnMouseExited(e -> lblRemove.setStyle(""));
        lblRemove.setOnMouseClicked(event -> {
            getListView().getItems().remove(getItem());
            getListView().getSelectionModel().select(-1);
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);

        if (item != null && !empty) {
            label.setText(item);
            setGraphic(hbox);
        }
    }
}