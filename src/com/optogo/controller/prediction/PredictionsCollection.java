package com.optogo.controller.prediction;

import java.util.Map;

public class PredictionsCollection {
    private Map<String, Float> diseasePredictions;
    private Map<String, Float> medicationPrediction;
    private Map<String, Float> procedurePrediction;

    public PredictionsCollection() {
    }

    public PredictionsCollection(Map<String, Float> diseasePredictions, Map<String, Float> medicationPrediction, Map<String, Float> procedurePrediction) {
        this.diseasePredictions = diseasePredictions;
        this.medicationPrediction = medicationPrediction;
        this.procedurePrediction = procedurePrediction;
    }

    public Map<String, Float> getDiseasePredictions() {
        return diseasePredictions;
    }

    public void setDiseasePredictions(Map<String, Float> diseasePredictions) {
        this.diseasePredictions = diseasePredictions;
    }

    public Map<String, Float> getMedicationPrediction() {
        return medicationPrediction;
    }

    public void setMedicationPrediction(Map<String, Float> medicationPrediction) {
        this.medicationPrediction = medicationPrediction;
    }

    public Map<String, Float> getProcedurePrediction() {
        return procedurePrediction;
    }

    public void setProcedurePrediction(Map<String, Float> procedurePrediction) {
        this.procedurePrediction = procedurePrediction;
    }
}
