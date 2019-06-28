package com.optogo.controller.prediction;

import com.optogo.controller.task.BayasInterfaceHandlerTask;
import com.optogo.graphics.Graph;
import com.optogo.service.bayes.SymptomRecommender;
import com.optogo.utils.StringFormatter;
import com.optogo.view.control.PredictedCondition;
import com.optogo.view.control.PredictionsPane;
import com.optogo.view.dialog.GraphDisplayDialog;
import com.optogo.view.dialog.RecommendedSymptomSelectionDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.stage.Stage;
import unbbayes.prs.exception.InvalidParentException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class PredictionListController {
    public ProgressBar progressBar;
    public Label lblProgress;

    public TabPane tabPane;

    private List<String> providedSymptoms;
    private BayasInterfaceHandlerTask bayesTask;

    private PredictionsCollection predictions;

    private PredictionsPane diseasesPreds;
    private PredictionsPane medPreds;
    private PredictionsPane procPreds;

    private Tab disTab, medTab, procTab;

    public void initialize() {
        progressBar.setVisible(false);
        lblProgress.setVisible(false);
    }

    public void setProvidedSymptoms(List<String> providedSymptoms) {
        this.providedSymptoms = new ArrayList<>(providedSymptoms);
    }

    public void setPredictions(PredictionsCollection predictionsCollection) {
        this.tabPane.getTabs().clear();

        this.predictions = predictionsCollection;

        diseasesPreds = new PredictionsPane();
        diseasesPreds.add(predictionsCollection.getDiseasePredictions(), "Conditions", PredictedCondition.Mode.SINGLE);
        medPreds = new PredictionsPane();
        medPreds.add(predictionsCollection.getMedicationPrediction(), "Medications", PredictedCondition.Mode.MULTIPLE);
        procPreds = new PredictionsPane();
        procPreds.add(predictionsCollection.getProcedurePrediction(), "Procedures", PredictedCondition.Mode.MULTIPLE);

        disTab = new Tab("Conditions");
        disTab.setContent(diseasesPreds);

        medTab = new Tab("Medications");
        medTab.setContent(medPreds);

        procTab = new Tab("Procedures");
        procTab.setContent(procPreds);

        tabPane.getTabs().addAll(disTab, medTab, procTab);
    }

    public Selection getSelected() {
        return new Selection(diseasesPreds.getSelected(), medPreds.getAllSelected(), procPreds.getAllSelected());
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
            recommendedSymptoms = SymptomRecommender.recommend(providedSymptoms, predictions.getDiseasePredictions());
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

    public void visualize(ActionEvent actionEvent) throws IOException {

        Graph graph = null;
        if (tabPane.getSelectionModel().getSelectedItem() == disTab) {
            graph = BayesPredictionGraphCreator.createForDieseases(providedSymptoms, predictions);
        } else if (tabPane.getSelectionModel().getSelectedItem() == medTab) {
            graph = BayesPredictionGraphCreator.createForMedications(predictions);
        } else {
            graph = BayesPredictionGraphCreator.createForProcedures(predictions);
        }

        GraphDisplayDialog.create(getStage(actionEvent), graph);
    }

}
