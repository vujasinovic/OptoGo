package com.optogo.controller.prediction;

import com.optogo.controller.task.BayasInterfaceHandlerTask;
import com.optogo.service.SymptomRecommender;
import com.optogo.utils.StringFormatter;
import com.optogo.view.control.PredictedCondition;
import com.optogo.view.control.Predictions;
import com.optogo.view.dialog.RecommendedSymptomSelectionDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.stage.Stage;
import unbbayes.prs.exception.InvalidParentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class PredictionListController {
    public ProgressBar progressBar;
    public Label lblProgress;

    public TabPane tabPane;

    private List<String> providedSymptoms;
    private BayasInterfaceHandlerTask bayesTask;

    private PredictionsCollection predictions;

    private Predictions diseasesPreds;
    private Predictions medPreds;
    private Predictions procPreds;

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

        diseasesPreds = new Predictions();
        diseasesPreds.add(predictionsCollection.getDiseasePredictions(), "Conditions", PredictedCondition.Mode.SINGLE);
        medPreds = new Predictions();
        medPreds.add(predictionsCollection.getMedicationPrediction(), "Medications", PredictedCondition.Mode.MULTIPLE);
        procPreds = new Predictions();
        procPreds.add(predictionsCollection.getProcedurePrediction(), "Procedures", PredictedCondition.Mode.MULTIPLE);

        Tab disTab = new Tab("Conditions");
        disTab.setContent(diseasesPreds);

        Tab medTab = new Tab("Medications");
        medTab.setContent(medPreds);

        Tab procTab = new Tab("Procedures");
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
/*
        Graph.Builder builder = Graph.Builder.create();

        List<String> symptoms = providedSymptoms.stream().map(StringFormatter::uderscoredLowerCase)
                .collect(Collectors.toList());

        for (String diseaseCapitalized : predictions.keySet()) {
            String disease = StringFormatter.uderscoredLowerCase(diseaseCapitalized);

            builder.addNode(GraphNode.Builder.create(disease)
                    .setText(diseaseCapitalized).setWeight(predictions.get(diseaseCapitalized)));

            Map<String, Float> symptomsWithProbabilities =
                    DiseaseSymptomParser.getSymptomsWithProbabilities(DISEASE_SYMPTOM_FILEPATH, disease);

            for (String symptom : symptoms) {
                Float probability = symptomsWithProbabilities.get(symptom);
                if(probability != null) {
                    builder.link(symptom, probability, disease);
                }
            }

        }

        GraphDisplayDialog.create(getStage(actionEvent), builder.build());
*/
    }

}
