package com.optogo.service.bayes;

import com.optogo.utils.enums.DiseaseName;
import com.optogo.utils.parse.DiseaseSymptomParser;

import java.io.IOException;
import java.util.Map;

public class DiseaseSimilarityCalculator {
    public static final String DISEASE_SYMPTOM_FILEPATH = "resources/symptom_disease.txt";

    public static double getSimilarity(DiseaseName disease1, DiseaseName disease2) throws IOException {
        Map<String, Float> symptomsWithProbabilities1 = DiseaseSymptomParser.getSymptomsWithProbabilities(DISEASE_SYMPTOM_FILEPATH, disease1.toString());
        Map<String, Float> symptomsWithProbabilities2 = DiseaseSymptomParser.getSymptomsWithProbabilities(DISEASE_SYMPTOM_FILEPATH, disease2.toString());

//        System.out.println(symptomsWithProbabilities1.size());
//        System.out.println(symptomsWithProbabilities2.size());

        double symptomSimilarities = 0;
        for (Map.Entry<String, Float> symptom1 : symptomsWithProbabilities1.entrySet()) {
            for (Map.Entry<String, Float> symptom2 : symptomsWithProbabilities2.entrySet()) {
                if (symptom1.getKey().equals(symptom2.getKey())) {
                    symptomSimilarities += Math.min(symptom1.getValue(), symptom2.getValue()) / Math.max(symptom1.getValue(), symptom2.getValue());
                }
            }
        }

        return symptomSimilarities / Math.min(symptomsWithProbabilities1.size(), symptomsWithProbabilities2.size());
    }
}
