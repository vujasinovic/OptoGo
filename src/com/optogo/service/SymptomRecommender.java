package com.optogo.service;

import com.optogo.utils.parse.DiseaseSymptomParser;
import unbbayes.prs.exception.InvalidParentException;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author avujasinovic
 * Class that is used to recommend other symptoms for certain disease.
 */
public class SymptomRecommender {

    public static final String DISEASE_SYMPTOM_FILE = "resources/disease_symptom.txt";

    /**
     * Method that recommends additional symptoms for certain disease which are not provided by patient.
     * This helps increasing precision. Diseases with probability less then 50% are not considered.
     * @param providedSymptoms
     * @return
     * @throws FileNotFoundException
     * @throws InvalidParentException
     */
    public static Set<String> recommend(List<String> providedSymptoms) throws FileNotFoundException, InvalidParentException {
        BayesInferenceHandler bayesInferenceHandler = new BayesInferenceHandler();
        Map<String, Float> diseases = bayesInferenceHandler.createNodes(providedSymptoms);
        List<String> diseaseNames = new ArrayList<>();

        for (Map.Entry<String, Float> entry : diseases.entrySet()) {
            if (entry.getValue() >= 0.50f) {
                diseaseNames.add(entry.getKey());
            }
        }

        Set<String> symptoms = new HashSet<>();
        for (String d : diseaseNames) {
            List<String> allSymptoms = DiseaseSymptomParser.getSymptoms(DISEASE_SYMPTOM_FILE, d);
            System.err.println("Disease: " + d);
            for (String s : allSymptoms) {
                if (!providedSymptoms.contains(s)) {
                    System.out.println(String.format("How about %s?", s));
                }
                symptoms.add(s);
            }
            System.out.println("\n");
        }

        System.out.println("Symptoms:");
        for (String s : symptoms) {
            System.out.println(s);
        }
        return symptoms;
    }

    //TODO remove after testing
    public static void main(String[] args) throws FileNotFoundException, InvalidParentException {
        recommend(Arrays.asList("lacrimation", "pain_in_eye", "symptoms_of_eye"));
    }

}
