package com.optogo.view.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AutoCompleteTextField extends TextField {
    private final ObservableList<String> entries = FXCollections.observableArrayList();

    private ContextMenu entriesPopup;

    private Consumer<String> onClick;

    public AutoCompleteTextField() {
        super();
        entriesPopup = new ContextMenu();
        entriesPopup.setStyle("-fx-cursor: hand;");

        textProperty().addListener((observableValue, s, s2) -> show());
        setOnMouseClicked(mouseEvent -> show());

        focusedProperty().addListener((observableValue, aBoolean, aBoolean2) -> entriesPopup.hide());
    }

    private void show() {
        LinkedList<String> searchResult = new LinkedList<>();
        searchResult.addAll(entries.stream()
                .filter(s -> s.toLowerCase().contains(getText().toLowerCase())).collect(Collectors.toList()));

        if (entries.size() > 0) {
            populatePopup(searchResult);
            if (!entriesPopup.isShowing()) {
                entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
            }
        } else {
            entriesPopup.hide();
        }
    }

    public ObservableList<String> getEntries() {
        return entries;
    }

    private void populatePopup(List<String> searchResult) {
        List<MenuItem> menuItems = new LinkedList<>();
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            MenuItem item = new MenuItem(result);
            item.setStyle("-fx-cursor: hand;");

            item.setOnAction(actionEvent -> {
                setText("");
                if(onClick != null)
                    onClick.accept(result);
                entriesPopup.hide();
            });
            menuItems.add(item);
        }

        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
        entriesPopup.setMinWidth(getWidth());
    }

    public void setOnClick(Consumer<String> onClick) {
        this.onClick = onClick;
    }

}