package com.optogo.controller;

import com.optogo.view.control.OptionSelection;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class OptionSelectionListController {
    @FXML
    private Button btnContinue;
    @FXML
    private VBox vbox;

    public void initialize() {

    }

    public void setOptions(List<String> options) {
        vbox.getChildren().clear();
        for (String option : options) {
            vbox.getChildren().add(new OptionSelection(option));
        }
    }

    public List<String> getSelected() {
        List<String> selected = new ArrayList<>();

        for (Node node : vbox.getChildren()) {
            OptionSelection optionSelection = (OptionSelection) node;
            if (optionSelection.getValue()) {
                selected.add(optionSelection.getName());
            }
        }

        return selected;
    }

    private Stage getStage(Event event) {
        return (Stage) ((Control) event.getSource()).getScene().getWindow();
    }


    public void ok(ActionEvent actionEvent) {
        getStage(actionEvent).close();
    }

}
