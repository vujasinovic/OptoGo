package com.optogo.controller.prediction;

import com.optogo.graphics.Graph;
import com.optogo.graphics.GraphNode;
import com.optogo.utils.StringFormatter;
import com.optogo.utils.parse.DiseaseMedicationParser;
import com.optogo.utils.parse.DiseaseProcedureParser;
import com.optogo.utils.parse.DiseaseSymptomParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BayesPredictionGraphCreator {
    private static final String DISEASE_SYMPTOM_FILEPATH = "resources/symptom_disease.txt";
    private static final String DISEASE_MEDICATION_FILEPATH = "resources/disease_medication.txt";
    private static final String DISEASE_PROCEDURE_FILEPATH = "resources/disease_procedure.txt";

    public static Graph createForDieseases(List<String> providedSymptoms, PredictionsCollection predictions) throws IOException {
        Graph.Builder builder = Graph.Builder.create();

        List<String> symptoms = providedSymptoms.stream().map(StringFormatter::uderscoredLowerCase)
                .collect(Collectors.toList());

        for (String diseaseCapitalized : predictions.getDiseasePredictions().keySet()) {
            String disease = StringFormatter.uderscoredLowerCase(diseaseCapitalized);

            builder.addNode(GraphNode.Builder.create(disease)
                    .setText(diseaseCapitalized).setWeight(predictions.getDiseasePredictions().get(diseaseCapitalized)));

            Map<String, Float> symptomsWithProbabilities =
                    DiseaseSymptomParser.getSymptomsWithProbabilities(DISEASE_SYMPTOM_FILEPATH, disease);

            for (String symptom : symptoms) {
                Float probability = symptomsWithProbabilities.get(symptom);
                if (probability != null) {
                    builder.link(symptom, probability, disease);
                }
            }

        }

        return builder.build();
    }

    public static Graph createForMedications(PredictionsCollection predictions) throws IOException {
        Graph.Builder builder = Graph.Builder.create();

        List<String> diseases = predictions.getDiseasePredictions().keySet().stream().map(StringFormatter::uderscoredLowerCase)
                .collect(Collectors.toList());

        for (String disease : diseases) {
            builder.addNode(GraphNode.Builder.create(disease).setText(StringFormatter.capitalizeWord(disease))
                    .setWeight(predictions.getDiseasePredictions().get(StringFormatter.capitalizeWord(disease))));
        }

        for (String medCapitalized : predictions.getMedicationPrediction().keySet()) {
            String medication = StringFormatter.uderscoredLowerCase(medCapitalized);

            builder.addNode(GraphNode.Builder.create(medication)
                    .setText(medCapitalized).setWeight(predictions.getMedicationPrediction().get(medCapitalized)));

            Map<String, Float> symptomsWithProbabilities =
                    DiseaseMedicationParser.getDiseasesWithProbabilities(DISEASE_MEDICATION_FILEPATH, medication);

            for (String symptom : diseases) {
                Float probability = symptomsWithProbabilities.get(symptom);
                if (probability != null) {
                    builder.link(symptom, probability, medication);
                }
            }

        }

        return builder.build();
    }

    public static Graph createForProcedures(PredictionsCollection predictions) throws IOException {
        Graph.Builder builder = Graph.Builder.create();

        List<String> diseases = predictions.getDiseasePredictions().keySet().stream().map(StringFormatter::uderscoredLowerCase)
                .collect(Collectors.toList());

        for (String disease : diseases) {
            builder.addNode(GraphNode.Builder.create(disease).setText(StringFormatter.capitalizeWord(disease))
                    .setWeight(predictions.getDiseasePredictions().get(StringFormatter.capitalizeWord(disease))));
        }

        for (String medCapitalized : predictions.getProcedurePrediction().keySet()) {
            String medication = StringFormatter.uderscoredLowerCase(medCapitalized);

            builder.addNode(GraphNode.Builder.create(medication)
                    .setText(medCapitalized).setWeight(predictions.getProcedurePrediction().get(medCapitalized)));

            Map<String, Float> diseasesWithProbabilities = DiseaseProcedureParser
                    .getDiseasesWithProbabilities(DISEASE_PROCEDURE_FILEPATH, medication);

            for (String symptom : diseases) {
                Float probability = diseasesWithProbabilities.get(symptom);
                if (probability != null) {
                    builder.link(symptom, probability, medication);
                }
            }

        }

        return builder.build();
    }

}
