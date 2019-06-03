package com.optogo.controller;

import com.optogo.service.BayesInferenceHandler;
import com.optogo.utils.MapUtil;
import com.optogo.utils.StringFormatter;
import com.optogo.view.dialog.ConditionSearchDialog;
import com.optogo.view.dialog.RecomendedSymptomSelectionDialog;
import com.optogo.view.dialog.SelectPredictedConditionDialog;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import unbbayes.prs.exception.InvalidParentException;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ExaminationController {

    public ProgressBar pb;
    public Label lblProgress;
    @FXML
    private BorderPane symptomSelection;

    @FXML
    private Label txtCondition;
    @FXML
    private Accordion diagnosisAccordion;
    @FXML
    private TitledPane diagnosisPane;
    @FXML
    private Accordion leftAccordion;
    @FXML
    private TitledPane symptomsPanel;
    @FXML
    private GridPane diagnosisGrid;

    @FXML
    private SymptomSelectionController symptomSelectionController;

    public void initialize() {
        leftAccordion.setExpandedPane(symptomsPanel);
        symptomsPanel.setCollapsible(false);

        diagnosisAccordion.setExpandedPane(diagnosisPane);
        diagnosisPane.setCollapsible(false);
    }

    public void select(ActionEvent actionEvent) {
        String selection = ConditionSearchDialog.create(getStage(actionEvent)).getSelected();
        if (selection != null)
            txtCondition.setText(selection);
    }

    private Stage getStage(Event event) {
        return (Stage) ((Control) event.getSource()).getScene().getWindow();
    }

    public void predict(ActionEvent actionEvent) {
        Task<Map<String, Float>> task = getPredictionTask(convert(symptomSelectionController.getSelected()));
        new Thread(task).start();
        task.setOnSucceeded(workerStateEvent -> {
            Map<String, Float> predictions = null;
            try {
                predictions = task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Map<String, Float> predictionsFormatted = new LinkedHashMap<>();
            for (String key : predictions.keySet()) {
                predictionsFormatted.put(StringFormatter.capitalizeWord(key), predictions.get(key));
            }

            MapUtil.sortByValue(predictionsFormatted);
            predictionsFormatted = MapUtil.reverse(predictionsFormatted);

            SelectPredictedConditionDialog selectPredictedConditionDialog = SelectPredictedConditionDialog.create(getStage(actionEvent), predictionsFormatted);
            txtCondition.setText(selectPredictedConditionDialog.getSelected());
        });
    }


    private List<String> convert(List<String> symptoms) {
        return symptoms.stream().map(StringFormatter::uderscoredLowerCase).collect(Collectors.toList());
    }

    /**
     * Sorts predictions by value and formats keys.
     *
     * @return
     */
    private Map<String, Float> getPredictions() {
        return null;
    }

    private Task<Map<String, Float>> getPredictionTask(List<String> symptoms) {
        Task<Map<String, Float>> task = new Task<Map<String, Float>>() {
            @Override
            protected Map<String, Float> call() throws Exception {
                BayesInferenceHandler bayesInferenceHandler = new BayesInferenceHandler();
                bayesInferenceHandler.setListener((current, max, message) -> {
                    Platform.runLater(() -> {
                        pb.setProgress((current * 1d / max));
                        lblProgress.setText(message);
                    });
                });

                return bayesInferenceHandler.createNodes(symptoms);
            }
        };

        return task;
    }

}
