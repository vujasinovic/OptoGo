package com.optogo.utils.parse;

import com.optogo.utils.enums.DiseaseName;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author avujasinovic
 */
public class DiseaseProcedureParser {

    public static final String DISEASE_PROCEDURE = "disease_procedure(";

    public static List<String> getProcedures(String filePath, String diseaseName) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        LinkedHashSet<String> procedures = new LinkedHashSet<>();
        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.startsWith(DISEASE_PROCEDURE + diseaseName.toLowerCase())) {
                procedures.add(tempLine.split("\\(")[1].split(",")[1].trim());
            }
        }
        List<String> retVal = new ArrayList<>();
        retVal.addAll(procedures);
        return retVal;
    }

    public static Map<String, Float> getProceduresWithProbabilities(String filePath, String diseaseName) throws IOException {
        Map<String, Float> proceduresWithProbabilities = new HashMap<>();
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
            String procedure = parts[1].trim();
            Float probability = Float.valueOf(parts[2].trim()) / 100f;

            if (disease.equalsIgnoreCase(diseaseName)) {
                proceduresWithProbabilities.put(procedure, probability);
            }

        }
        return proceduresWithProbabilities;
    }

    public static Map<String, Float> getAllProceduresWithProbabilities(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        Map<String, Float> proceduresWithProbabilities = new HashMap<>();
        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.startsWith(DISEASE_PROCEDURE)) {
                String procedure = tempLine.split("\\(")[1].split(",")[1].trim();
                Float probability = Float.parseFloat(tempLine.split("\\(")[1].split(",")[2].split("\\)")[0].trim()) * 0.01f;
                proceduresWithProbabilities.put(procedure, probability);
            }
        }
        return proceduresWithProbabilities;
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

    public static void main(String[] args) throws IOException {
        getProceduresWithProbabilities("resources/disease_procedure.txt", DiseaseName.CATARACT.toString());
    }
}
