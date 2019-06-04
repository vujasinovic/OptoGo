package com.optogo.utils.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author avujasinovic
 */
public class DiseaseSymptomParser {

    public static final String DISEASE_SYMPTOM = "disease_symptom(";

    /**
     * @param filePath
     * @param diseaseName
     * @return all symptoms that indicates on disease
     * @throws FileNotFoundException if there is no file found on provided path
     */
    public static List<String> getSymptoms(String filePath, String diseaseName) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        LinkedHashSet<String> symptoms = new LinkedHashSet<>();
        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.startsWith(DISEASE_SYMPTOM + diseaseName.toLowerCase())) {
                symptoms.add(tempLine.split("\\(")[1].split(",")[1].trim());
            }
        }
        List<String> retVal = new ArrayList<>();
        retVal.addAll(symptoms);
        return retVal;
    }

    /**
     * @param filePath
     * @param diseaseName
     * @return Map<String ,   Float> that represents all symptoms with their probabilities for specific disease
     * Symptoms are represented as String (key) and probabilities as Float (value)
     * @throws FileNotFoundException if there is no file found on provided path
     */
    public static Map<String, Float> getSymptomsWithProbabilities(String filePath, String diseaseName) throws IOException {
        Map<String, Float> symptomsWithProbabilities = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("%")) {
                continue;
            }

            line = line.replaceAll("(.*?)\\(", "");
            line = line.replaceAll("\\)(.*?)", "");

            String[] parts = line.split(",");
            String symptom = parts[0].trim();
            String disease = parts[1].trim();
            Float probability = Float.valueOf(parts[2].trim()) / 100f;

            if (disease.equalsIgnoreCase(diseaseName)) {
                symptomsWithProbabilities.put(symptom, probability);
            }

        }

        return symptomsWithProbabilities;
    }

    /**
     * @param filePath
     * @return Map<String ,   Float> that represents all symptoms with their probabilities
     * Symptoms are represented as String (key) and probabilities as Float (value)
     * @throws FileNotFoundException if there is no file found on provided path
     */
    public static Map<String, Float> getAllSymptomsWithProbabilities(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        Map<String, Float> symptomsWithProbabilities = new HashMap<>();
        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.startsWith(DISEASE_SYMPTOM)) {
                String symptom = tempLine.split("\\(")[1].split(",")[1].trim();
                Float probability = Float.parseFloat(tempLine.split("\\(")[1].split(",")[2].split("\\)")[0].trim()) * 0.01f;
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
    public static Set<String> getDiseases(String filePath) throws IOException {
        Set<String> retVal = new HashSet<>();

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("%")) {
                continue;
            }

            line = line.replaceAll("(.*?)\\(", "");
            line = line.replaceAll("\\)(.*?)", "");

            String[] parts = line.split(",");
            if (parts.length != 3) {
                System.err.println("Invalid entry: " + line);
            }

            String disease = parts[1].trim();
            retVal.add(disease);
        }
        return retVal;
    }
}
