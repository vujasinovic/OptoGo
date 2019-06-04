package com.optogo.controller.task;

import com.optogo.service.BayesInferenceHandler;
import com.optogo.service.HandlerProgressListener;
import com.optogo.utils.MapUtil;
import com.optogo.utils.StringFormatter;
import javafx.concurrent.Task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BayasInterfaceHandlerTask extends Task<Map<String, Float>> {
    private List<String> symptoms;
    private BayesInferenceHandler bayesInferenceHandler;

    public BayasInterfaceHandlerTask(List<String> symptoms) {
        this.symptoms = convert(symptoms);
        this.bayesInferenceHandler = new BayesInferenceHandler();
    }

    private List<String> convert(List<String> symptoms) {
        return symptoms.stream().map(StringFormatter::uderscoredLowerCase).collect(Collectors.toList());
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    @Override
    protected Map<String, Float> call() throws Exception {
        Map<String, Float> predictions = bayesInferenceHandler.createNodes(symptoms);

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
    }
}
