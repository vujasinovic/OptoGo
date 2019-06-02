package com.optogo.utils.parse;

import com.optogo.utils.enums.DiseaseName;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ParseDiseaseSymptom {

    /**
     * @param filePath
     * @param diseaseName
     * @return all symptoms that indicates on disease
     * @throws FileNotFoundException if there is no file found on provided path
     */
    public static List<String> getSymptoms(String filePath, DiseaseName diseaseName) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        LinkedHashSet<String> symptoms = new LinkedHashSet<>();
        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.startsWith("disease_symptom(" + diseaseName.toString().toLowerCase())) {
                symptoms.add(tempLine.split("\\(")[1].split(",")[1]);
            }
        }
        List<String> retVal = new ArrayList<>();
        retVal.addAll(symptoms);
        return retVal;
    }

    /**
     * @param filePath
     * @param diseaseName
     * @return Map<String, Float> that represents all symptoms with their probabilities
     * Symptoms are represented as String (key) and probabilities as Float (value)
     * @throws FileNotFoundException if there is no file found on provided path
     */
    public static Map<String, Float> getSymptomsWithProbabilities(String filePath, DiseaseName diseaseName) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        Map<String, Float> symptomsWithProbabilities = new HashMap<>();
        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.startsWith("disease_symptom(" + diseaseName.toString().toLowerCase())) {
                String symptom = tempLine.split("\\(")[1].split(",")[1];
                Float probability = Float.parseFloat(tempLine.split("\\(")[1].split(",")[2].split("\\)")[0]) * 0.01f;
                symptomsWithProbabilities.put(symptom, probability);
            }
        }
        return symptomsWithProbabilities;
    }

    /**
     * @param filePath
     * @return List of diseases found in file on provided file path
     * @throws FileNotFoundException if there is no file found on provided path
     */
    public static List<String> getDiseases(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        LinkedHashSet<String> diseases = new LinkedHashSet<>();
        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.startsWith("disease_symptom(")) {
                diseases.add(tempLine.split("\\(")[1].split(",")[0]);
            }
        }
        List<String> retVal = new ArrayList<>();
        retVal.addAll(diseases);

        return retVal;
    }
}
