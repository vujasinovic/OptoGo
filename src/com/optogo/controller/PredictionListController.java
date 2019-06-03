package com.optogo.controller;

import com.optogo.view.control.PredictedCondition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class PredictionListController {
    @FXML
    private VBox vbox;

    public void setPredictions(Map<String, Float> predictions) {
        vbox.getChildren().clear();

        ToggleGroup group = new ToggleGroup();

        boolean select = true;
        for (String key : predictions.keySet()) {
            PredictedCondition predictedCondition = new PredictedCondition(key, predictions.get(key));
            predictedCondition.setGroup(group);
            vbox.getChildren().add(predictedCondition);

            if(select) {
                select = false;
                predictedCondition.setSelected(true);
            }
        }

    }

    public String getSelected() {
        for (Node child : vbox.getChildren()) {
            PredictedCondition pc = (PredictedCondition) child;
            if(pc.isSelected())
                return pc.getName();
        }

        return null;
    }

    public void select(ActionEvent actionEvent) {
        getStage(actionEvent).close();
    }

    private Stage getStage(Event event) {
        return (Stage) ((Control) event.getSource()).getScene().getWindow();
    }

}
