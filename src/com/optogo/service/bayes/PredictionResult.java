package com.optogo.service.bayes;

import java.util.Map;

public class PredictionResult {
    private Map<String, Float> prediction;
    private Map<String, Float> source;

    public PredictionResult(Map<String, Float> prediction, Map<String, Float> source) {
        this.prediction = prediction;
        this.source = source;
    }

    public PredictionResult() {
    }

    public Map<String, Float> getPrediction() {
        return prediction;
    }

    public void setPrediction(Map<String, Float> prediction) {
        this.prediction = prediction;
    }

    public Map<String, Float> getSource() {
        return source;
    }

    public void setSource(Map<String, Float> source) {
        this.source = source;
    }
}
