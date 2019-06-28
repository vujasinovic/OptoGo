package com.optogo.controller.task;

import com.optogo.controller.prediction.PredictionsCollection;
import com.optogo.model.*;
import com.optogo.service.CBRMedicalRecommenderHandler;
import com.optogo.utils.StringFormatter;
import javafx.concurrent.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CBRPredictionTask extends Task<PredictionsCollection> {

    private Patient patient;
    private Disease disease;

    public CBRPredictionTask(Patient patient, Disease disease) {
        this.patient = patient;
        this.disease = disease;
    }

    @Override
    protected PredictionsCollection call() throws Exception {
        CBRMedicalRecommenderHandler cbrHandler = new CBRMedicalRecommenderHandler();
        List<MedicalPrescription> predictions = cbrHandler.predict(patient, disease);

        Set<String> medPreds = predictions.stream()
                .map(MedicalPrescription::getMedication).map(Medication::getName).map(Enum::name)
                .map(StringFormatter::capitalizeWord).collect(Collectors.toSet());

        Set<String> procPreds = predictions.stream()
                .map(MedicalPrescription::getProcedure).map(Procedure::getTitle).map(Enum::name)
                .map(StringFormatter::capitalizeWord).collect(Collectors.toSet());

        Map<String, Float> medsFormated = new HashMap<>();
        for(String med : medPreds)
            medsFormated.put(med, -1f);
        Map<String, Float> procFormated = new HashMap<>();
        for (String proc : procPreds) {
            procFormated.put(proc, -1f);
        }

        return new PredictionsCollection(null, medsFormated, procFormated);
    }

}
