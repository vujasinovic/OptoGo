package com.optogo.controller;

import com.optogo.repository.impl.SymptomRepository;
import com.optogo.utils.StringFormatter;
import com.optogo.view.control.AutoCompleteTextField;
import com.optogo.view.control.XCell;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SymptomSelectionController implements ListChangeListener<String> {

    public ListView<String> listSymptoms;
    public BorderPane borderPane;

    private AutoCompleteTextField txtAvailableSymptoms;

    private SymptomRepository symptomRepository;

    public void initialize() {
        txtAvailableSymptoms = new AutoCompleteTextField();
        txtAvailableSymptoms.setMinHeight(txtAvailableSymptoms.getPrefHeight());
        txtAvailableSymptoms.setPromptText("Search Symptom");

        symptomRepository = new SymptomRepository();

        borderPane.setTop(txtAvailableSymptoms);

        txtAvailableSymptoms.setOnClick(this::select);
        txtAvailableSymptoms.getEntries().addAll(symptomRepository.findAll()
                .stream().map(s -> StringFormatter.capitalizeWord(s.getName().name())).collect(Collectors.toList()));

        txtAvailableSymptoms.getEntries().sort(String::compareTo);

        listSymptoms.setCellFactory(p -> new XCell());
        listSymptoms.getItems().addListener(this);
    }

    private void select(String symptom) {
        txtAvailableSymptoms.getEntries().remove(symptom);
        listSymptoms.getItems().add(symptom);
    }

    @Override
    public void onChanged(Change<? extends String> change) {
        change.next();
        if (change.wasRemoved()) {
            txtAvailableSymptoms.getEntries().add(change.getRemoved().get(0));
            txtAvailableSymptoms.getEntries().sort(String::compareTo);
        }
    }

    public List<String> getSelected() {
        return Arrays.asList(listSymptoms.getItems().toArray(new String[0]));
    }

}
