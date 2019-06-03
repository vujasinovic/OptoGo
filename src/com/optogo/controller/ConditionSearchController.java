package com.optogo.controller;

import com.optogo.model.Disease;
import com.optogo.repository.impl.DiseaseRepository;
import com.optogo.utils.StringFormatter;
import com.optogo.utils.enums.DiseaseName;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class ConditionSearchController implements ChangeListener {
    @FXML private
    TextField txtSearch;
    @FXML private
    ListView<String> listView;
    @FXML private
    Button btnSelect;
    @FXML private
    Button btnCancel;

    private String selected = null;

    private DiseaseRepository repository;

    private ObservableList<String> availableConditions = FXCollections.observableArrayList();

    public void initialize() {
        repository = new DiseaseRepository();

        availableConditions.addAll(repository.findAll().stream()
                .map(Disease::getName).map(DiseaseName::name).map(StringFormatter::capitalizeWord)
                .collect(Collectors.toList()));

        listView.getItems().addAll(availableConditions);

        txtSearch.textProperty().addListener(this);

    }

    public void search() {
        String text = txtSearch.getText().trim();
        if (text.isEmpty()) {
            return;
        }

        listView.getItems().clear();
        listView.getItems().addAll(filter(text));
    }

    private ObservableList<String> filter(String text) {
        return availableConditions.filtered(s -> s.toLowerCase().contains(text.toLowerCase()));
    }

    public void cancel(ActionEvent actionEvent) {
        selected = null;
        getStage(actionEvent).close();
    }

    public void select(ActionEvent actionEvent) {
        selected = listView.getSelectionModel().getSelectedItem();
        if (selected == null && !listView.getItems().isEmpty()) {
            selected = listView.getItems().get(0);
        }

        getStage(actionEvent).close();
    }

    private Stage getStage(Event event) {
        return (Stage) ((Control) event.getSource()).getScene().getWindow();
    }

    public void clicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() >= 2) {
            selected = listView.getSelectionModel().getSelectedItem();
            getStage(mouseEvent).close();
        }
    }

    public String getSelected() {
        return selected;
    }

    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1) {
        search();
    }

}
