package com.optogo.utils.parse;

import com.optogo.utils.enums.DiseaseName;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParseDiseaseSymptom {
    public static List<String> getSymptoms(String filePath, DiseaseName diseaseName) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        List<String> symptoms = new ArrayList<>();
        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.startsWith("disease_symptom(" + diseaseName)) {
                symptoms.add(tempLine.split("\\(")[1].split(",")[1]);
            }
        }
        return symptoms;
    }
}
