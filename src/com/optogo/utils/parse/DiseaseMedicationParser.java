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
public class DiseaseMedicationParser {

    public static final String DISEASE_MEDICATION = "disease_medication(";

    public static List<String> getMedications(String filePath, String diseaseName) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        LinkedHashSet<String> medications = new LinkedHashSet<>();
        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.startsWith(DISEASE_MEDICATION + diseaseName.toLowerCase())) {
                medications.add(tempLine.split("\\(")[1].split(",")[1].trim());
            }
        }
        List<String> retVal = new ArrayList<>();
        retVal.addAll(medications);
        return retVal;
    }

    public static Set<String> getAllMedications(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        Set<String> medications = new HashSet<>();
        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.startsWith(DISEASE_MEDICATION)) {
                medications.add(tempLine.split("\\(")[1].split(",")[1].trim());
            }
        }
        return medications;
    }

    public static Map<String, Float> getMedicationsWithProbabilities(String filePath, String diseaseName) throws IOException {
        Map<String, Float> medicationsWithProbabilities = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("%")) {
                continue;
            }

            line = line.replaceAll("(.*?)\\(", "");
            line = line.replaceAll("\\)(.*?)", "");

            String[] parts = line.split(",");
            String disease = parts[0].trim();
            String medication = parts[1].trim();
            Float probability = Float.valueOf(parts[2].trim()) / 100f;

            if (disease.equalsIgnoreCase(diseaseName)) {
                medicationsWithProbabilities.put(medication, probability);
            }

        }

        return medicationsWithProbabilities;
    }

    public static Map<String, Float> getAllMedicationsWithProbabilities(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        Map<String, Float> medicationsWithProbabilities = new HashMap<>();
        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.startsWith(DISEASE_MEDICATION)) {
                String medication = tempLine.split("\\(")[1].split(",")[1].trim();
                Float probability = Float.parseFloat(tempLine.split("\\(")[1].split(",")[2].split("\\)")[0].trim()) * 0.01f;
                medicationsWithProbabilities.put(medication, probability);
            }
        }
        return medicationsWithProbabilities;
    }

    public static Map<String, Float> getDiseasesWithProbabilities(String filePath, String medicationName) throws IOException {
        Map<String, Float> diseasesWithProbabilities = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("%")) {
                continue;
            }

            line = line.replaceAll("(.*?)\\(", "");
            line = line.replaceAll("\\)(.*?)", "");

            String[] parts = line.split(",");
            //TODO kad se ubaci fajl medication_disease korigovati indekse.
            String disease = parts[0].trim();
            String medication = parts[1].trim();
            Float probability = Float.valueOf(parts[2].trim()) / 100f;

            if (medication.equalsIgnoreCase(medicationName)) {
                diseasesWithProbabilities.put(disease, probability);
            }

        }
        return diseasesWithProbabilities;
    }


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

            String disease = parts[0].trim();
            retVal.add(disease);
        }
        return retVal;
    }

//    public static void main(String[] args) throws IOException {
//        getMedicationsWithProbabilities("resources/disease_medication.txt", DiseaseName.CATARACT.toString());
//    }
}
