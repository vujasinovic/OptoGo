package com.optogo.controller;

import com.optogo.view.control.AutoCompleteTextField;
import com.optogo.view.control.XCell;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SelectionController implements ListChangeListener<String> {

    public ListView<String> items;
    public BorderPane borderPane;

    private AutoCompleteTextField txtAvailable;

    public void initialize() {
        txtAvailable = new AutoCompleteTextField();
        txtAvailable.setMinHeight(txtAvailable.getPrefHeight());
        txtAvailable.setMaxWidth(Double.MAX_VALUE);

        txtAvailable.setPromptText("Search");

        borderPane.setTop(txtAvailable);

        txtAvailable.setOnClick(this::select);

        txtAvailable.getEntries().sort(String::compareTo);

        items.setCellFactory(p -> new XCell());
        items.getItems().addListener(this);
    }

    public void select(String item) {
        ObservableList<String> items = this.items.getItems();
        if (!items.contains(item)) {
            txtAvailable.getEntries().remove(item);
            items.add(item);
        }
    }

    public void selectAll(Collection<String> items) {
        for (String item : items) {
            select(item);
        }
    }

    @Override
    public void onChanged(Change<? extends String> change) {
        change.next();
        if (change.wasRemoved()) {
            txtAvailable.getEntries().add(change.getRemoved().get(0));
            txtAvailable.getEntries().sort(String::compareTo);
        }
    }

    public List<String> getSelected() {
        return Arrays.asList(items.getItems().toArray(new String[0]));
    }

    public void addItems(List<String> items) {
        txtAvailable.getEntries().addAll(items);
    }

}
