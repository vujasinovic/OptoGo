package com.optogo.controller.task;

import com.optogo.controller.prediction.PredictionsCollection;
import com.optogo.service.BayesInferenceHandler;
import com.optogo.service.HandlerProgressListener;
import com.optogo.service.MedicationBayesianNetwork;
import com.optogo.service.ProcedureBayesianNetwork;
import com.optogo.utils.MapUtil;
import com.optogo.utils.StringFormatter;
import javafx.concurrent.Task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BayasInterfaceHandlerTask extends Task<PredictionsCollection> {
    private List<String> symptoms;
    private final BayesInferenceHandler bayesInferenceHandler;
    private final MedicationBayesianNetwork medicationBayesianNetwork;
    private final ProcedureBayesianNetwork procedureBayesianNetwork;

    public BayasInterfaceHandlerTask(List<String> symptoms) {
        this.symptoms = convert(symptoms);
        this.bayesInferenceHandler = new BayesInferenceHandler();
        this.medicationBayesianNetwork = new MedicationBayesianNetwork();
        this.procedureBayesianNetwork = new ProcedureBayesianNetwork();
    }

    private List<String> convert(List<String> symptoms) {
        return symptoms.stream().map(StringFormatter::uderscoredLowerCase).collect(Collectors.toList());
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    @Override
    protected PredictionsCollection call() throws Exception {
        PredictionsCollection collection = new PredictionsCollection();

        Map<String, Float> diseases = bayesInferenceHandler.createNodes(symptoms);
        collection.setDiseasePredictions(format(diseases));

        Map<String, Float> medications = medicationBayesianNetwork.createNodes(diseases);
        collection.setMedicationPrediction(format(medications));

        Map<String, Float> procedures = procedureBayesianNetwork.createNodes(diseases);
        collection.setProcedurePrediction(format(procedures));

        return collection;
    }

    private Map<String, Float> format(Map<String, Float> predictions) {
        Map<String, Float> predictionsFormatted = new LinkedHashMap<>();
        for (String key : predictions.keySet()) {
            Float value = predictions.get(key);
            if(value == 0)
                continue;

            predictionsFormatted.put(StringFormatter.capitalizeWord(key), value);
        }

        MapUtil.sortByValue(predictionsFormatted);
        predictionsFormatted = MapUtil.reverse(predictionsFormatted);
        return predictionsFormatted;
    }

    public void setHandlerProgressListener(HandlerProgressListener listener) {
        bayesInferenceHandler.setListener(listener);
        medicationBayesianNetwork.setListener(listener);
        procedureBayesianNetwork.setListener(listener);
    }
}
