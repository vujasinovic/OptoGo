package com.optogo.service;

import com.optogo.utils.parse.DiseaseSymptomParser;
import unbbayes.prs.Edge;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import unbbayes.prs.exception.InvalidParentException;
import unbbayes.util.extension.bn.inference.IInferenceAlgorithm;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.optogo.service.BayesInferenceHandlerUtilities.print;
import static com.optogo.service.BayesInferenceHandlerUtilities.saveFile;

public class BayesInferenceHandler {

    public static final String DISEASE_SYMPTOM_FILEPATH = "resources/disease_symptom.txt";
    public static final float SYMPTOM_INIT_PROBABILITY = 0.5f;
    public static final String YES_STATE = "y";
    public static final String NO_STATE = "n";
    public static final int YES_INDEX = 0;
    public static final int NO_INDEX = 1;
    public static final String STATE_POSITIVE = "positive";
    public static final String STATE_NEGATIVE = "negative";

    public Map<String, Float> createNodes(List<String> symptoms) throws FileNotFoundException, InvalidParentException {
        List<String> diseases = DiseaseSymptomParser.getDiseases(DISEASE_SYMPTOM_FILEPATH);
        Map<String, Float> diseaseProbability = new HashMap<>();

        for (String disease : diseases) {
            ProbabilisticNetwork network = new ProbabilisticNetwork(disease);
            ProbabilisticNode diseaseNode = new ProbabilisticNode();
            initializeNode(diseaseNode, disease);
            network.addNode(diseaseNode);

            PotentialTable probDisease = diseaseNode.getProbabilityFunction();
            probDisease.addVariable(diseaseNode);

            Map<String, Float> symptomsWithProbabilities = DiseaseSymptomParser.getSymptomsWithProbabilities(DISEASE_SYMPTOM_FILEPATH, disease);

            createSymptomNodes(network, diseaseNode, symptomsWithProbabilities.keySet());

            PotentialTableValueCalculator calculator = new PotentialTableValueCalculator();
            probDisease.setValues(calculator.calculate(symptomsWithProbabilities));

            IInferenceAlgorithm algorithm = new JunctionTreeAlgorithm();
            algorithm.setNetwork(network);
            algorithm.run();

            setFindings(network, symptoms, symptomsWithProbabilities.keySet());

            try {
                network.updateEvidences();
            } catch (Exception e) {
                e.printStackTrace();
            }

            print(network);

            diseaseProbability.put(disease, diseaseNode.getMarginalAt(YES_INDEX));
            saveFile(network, disease);
        }
        BayesInferenceHandlerUtilities bayesInferenceHandlerUtilities = new BayesInferenceHandlerUtilities();
        return bayesInferenceHandlerUtilities.sortByValueAscending(diseaseProbability);
    }

    public void createSymptomNodes(ProbabilisticNetwork network, ProbabilisticNode diseaseNode, Set<String> symptoms) throws InvalidParentException {
        for (String s : symptoms) {
            ProbabilisticNode symptom = new ProbabilisticNode();
            symptom.setName(s);
            symptom.appendState(YES_STATE);
            symptom.appendState(NO_STATE);
            PotentialTable symptomTable = symptom.getProbabilityFunction();
            symptomTable.addVariable(symptom);

            symptomTable.setValue(YES_INDEX, SYMPTOM_INIT_PROBABILITY);
            symptomTable.setValue(NO_INDEX, SYMPTOM_INIT_PROBABILITY);
            network.addNode(symptom);

            network.addEdge(new Edge(symptom, diseaseNode));
        }
    }

    private void setFindings(ProbabilisticNetwork network, List<String> providedSymptoms, Set<String> diseaseSymptoms) {
        for (String s : diseaseSymptoms) {
            ProbabilisticNode factNode = (ProbabilisticNode) network.getNode(s);
            if (providedSymptoms.contains(s)) {
                factNode.addFinding(YES_INDEX);
            } else {
                factNode.addFinding(NO_INDEX, false);
            }
        }
    }

    private void initializeNode(ProbabilisticNode node, String disease) {
        node.setName(disease);
        node.appendState(STATE_POSITIVE);
        node.appendState(STATE_NEGATIVE);
    }

//    public static void main(String[] args) throws FileNotFoundException, InvalidParentException {
//        List<String> symptoms = new ArrayList<>();
//        symptoms.add("eye_redness");
//        symptoms.add("pain_in_eye");
//        symptoms.add("cough");
//        symptoms.add("nasal_congestion");
//        symptoms.add("fever");
////        symptoms.add("itchiness_of_eye");
////        symptoms.add("swollen_eye");
////        symptoms.add("white_discharge_from_eye");
////        symptoms.add("lacrimation");
////        symptoms.add("symptoms_of_eye");
//
//        Map<String, Float> diseaseProbability = new HashMap<>();
//        diseaseProbability = createNodes(symptoms);
//        for (Map.Entry<String, Float> entry : diseaseProbability.entrySet()) {
//            System.out.println(entry.getKey() + " | " + entry.getValue());
//        }
//    }
}
