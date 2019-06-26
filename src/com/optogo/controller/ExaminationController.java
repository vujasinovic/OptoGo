package com.optogo.controller;

import com.optogo.controller.task.BayasInterfaceHandlerTask;
import com.optogo.utils.StringFormatter;
import com.optogo.view.dialog.ConditionSearchDialog;
import com.optogo.view.dialog.SelectPredictedConditionDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    private BayasInterfaceHandlerTask bayesTask;

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
        bayesTask = new BayasInterfaceHandlerTask(symptomSelectionController.getSelected());
        bayesTask.setOnSucceeded(workerStateEvent -> {
            predictionCompleted(actionEvent);
        });
        bayesTask.setHandlerProgressListener((current, max, message) -> {
            Platform.runLater(() -> {
                pb.setProgress((current * 1d / max));
                lblProgress.setText("Calculating: " + StringFormatter.capitalizeWord(message));
            });
        });
        new Thread(bayesTask).start();
    }

    private void predictionCompleted(Event actionEvent) {
        Map<String, Float> predictions = null;
        try {
            predictions = bayesTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }

        lblProgress.setText("Completed");

        SelectPredictedConditionDialog selectPredictedConditionDialog = SelectPredictedConditionDialog
                .create(getStage(actionEvent), symptomSelectionController.getSelected(), predictions);
        txtCondition.setText(selectPredictedConditionDialog.getSelected());
        symptomSelectionController.selectAll(selectPredictedConditionDialog.getExtendedSymptoms());
    }

}
