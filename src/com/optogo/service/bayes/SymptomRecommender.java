package com.optogo.service.bayes;

import com.optogo.utils.StringFormatter;
import com.optogo.utils.parse.DiseaseSymptomParser;
import unbbayes.prs.exception.InvalidParentException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author avujasinovic
 * Class that is used to recommend other symptoms for certain disease.
 */
public class SymptomRecommender {

    public static final String DISEASE_SYMPTOM_FILE = "resources/disease_symptom.txt";

    /**
     * Method that recommends additional symptoms for certain disease which are not provided by patient.
     * This helps increasing precision. Diseases with probability less then 50% ar  e not considered.
     * @param providedSymptoms
     * @return
     * @throws FileNotFoundException
     * @throws InvalidParentException
     */
    public static Set<String> recommend(List<String> providedSymptoms, Map<String, Float> predictions) throws IOException, InvalidParentException {
        Set<String> allSymptoms = new HashSet<>();
        Set<String> diseaseInPrediction = predictions.keySet();

        double avg = 0;
        for (String key : diseaseInPrediction) {
            avg += predictions.get(key);
        }
        avg /= predictions.size();

        for(String d : diseaseInPrediction)
            if(predictions.get(d) >= avg)
                allSymptoms.addAll(DiseaseSymptomParser.getSymptoms(DISEASE_SYMPTOM_FILE, d));

        allSymptoms.removeAll(providedSymptoms.stream().map(StringFormatter::underscoredLowerCase).collect(Collectors.toList()));

        return allSymptoms;
    }

}
