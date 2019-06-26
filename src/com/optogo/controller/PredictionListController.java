package com.optogo.controller;

import com.optogo.controller.task.BayasInterfaceHandlerTask;
import com.optogo.service.SymptomRecommender;
import com.optogo.utils.StringFormatter;
import com.optogo.view.control.PredictedCondition;
import com.optogo.view.dialog.RecommendedSymptomSelectionDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import unbbayes.prs.exception.InvalidParentException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class PredictionListController {
    public ProgressBar progressBar;
    public Label lblProgress;

    @FXML
    private VBox vbox;

    private List<String> providedSymptoms;
    private Map<String, Float> predictions;
    private BayasInterfaceHandlerTask bayesTask;

    public void initialize() {
        progressBar.setVisible(false);
        lblProgress.setVisible(false);
    }

    public void setProvidedSymptoms(List<String> providedSymptoms) {
        this.providedSymptoms = new ArrayList<>(providedSymptoms);
    }

    public void setPredictions(Map<String, Float> predictions) {
        this.predictions = predictions;

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

    public void improveResults(ActionEvent actionEvent) {
        Set<String> recommendedSymptoms = new HashSet<>();
        try {
            recommendedSymptoms = SymptomRecommender.recommend(providedSymptoms, predictions);
        } catch (IOException | InvalidParentException e) {
            e.printStackTrace();
        }

        RecommendedSymptomSelectionDialog dialog = RecommendedSymptomSelectionDialog.create(getStage(actionEvent), new ArrayList<>(recommendedSymptoms));
        providedSymptoms.addAll(dialog.getSelected());

        bayesTask = new BayasInterfaceHandlerTask(providedSymptoms);
        bayesTask.setOnSucceeded(workerStateEvent -> {
            try {
                setPredictions(bayesTask.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            lblProgress.setText("Completed");
        });
        bayesTask.setHandlerProgressListener((current, max, message) -> {
            Platform.runLater(() -> {
                progressBar.setProgress((current * 1d / max));
                lblProgress.setText("Calculating: " + StringFormatter.capitalizeWord(message));
            });
        });

        lblProgress.setVisible(true);
        progressBar.setVisible(true);

        new Thread(bayesTask).start();
    }

    public List<String> getExtraSymptoms() {
        return providedSymptoms;
    }

}
