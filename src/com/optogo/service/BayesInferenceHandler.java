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
import java.util.*;

import static com.optogo.service.BayesInferenceHandlerUtilities.print;
import static com.optogo.service.BayesInferenceHandlerUtilities.saveFile;

/**
 * @author avujasinovic
 * This class is used to form the Bayesian network. It contains methods that are used
 * to create network and it's nodes and edges between them. It also contains two helper
 * methods (setFindings and initializeNodes)
 */
public class BayesInferenceHandler {
    public static final String DISEASE_SYMPTOM_FILEPATH = "resources/disease_symptom.txt";
    public static final float SYMPTOM_INIT_PROBABILITY = 0.5f;
    public static final String YES_STATE = "y";
    public static final String NO_STATE = "n";
    public static final int YES_INDEX = 0;
    public static final int NO_INDEX = 1;
    public static final String STATE_POSITIVE = "positive";
    public static final String STATE_NEGATIVE = "negative";

    private HandlerProgressListener listener;

    /**
     * Iterates through all diseases and for each of them it creates independent Bayesian network with the name
     * of disease. Inside of each network it creates <code>diseaseNode</code> and initialize it's name and states.
     * After the node is added to network, it creates <code>PotentialTable</code> instance for that node.
     * Next, it creates nodes for all provided symptoms using the method <code>createSymptomNodes</code>.
     * After symptom nodes are created and added to network, it calculates all conditional probabilities and add them
     * to CPT (conditional probability table). Then it runs the algorithm, sets findings and updates the network with
     * new evidences. In the end it saves the file for each disease with <bold>.net</bold> extension which can be
     * opened in UnbBayes software.
     *
     * @param symptoms - Represents the symptoms that are collected directly from patient.
     * @return - sorted Map<String, Float> where key represents the name of Disease and value represents the calculated
     * probability based on provided symptoms
     * @throws FileNotFoundException  - if file that contains data relations between disease and symptom doesn't exists
     * @throws InvalidParentException - if it is not possible to create edge between two nodes
     */
    public Map<String, Float> createNodes(List<String> symptoms) throws FileNotFoundException, InvalidParentException {
        List<String> diseases = DiseaseSymptomParser.getDiseases(DISEASE_SYMPTOM_FILEPATH);
        Map<String, Float> diseaseProbability = new HashMap<>();

        int count = 0;
        for (String disease : diseases) {
            if (listener != null)
                listener.progressUpdated(++count, diseases.size(), disease);

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

    /**
     * Method that creates nodes for each symptom provided and edges between each symptom and disease.
     *
     * @param network     - Bayesian network in which nodes are inserted.
     * @param diseaseNode - represents the disease with which symptom node will be connected.
     * @param symptoms    - symptoms that are going to be inserted as nodes in Bayesian network.
     * @throws InvalidParentException - if it is not possible to create Edge between symptom and disease
     */
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

    /**
     * It sets state values of provided symptoms to 1.0 or 0.0 whether provided symptom is in the list of
     * all symptoms that are specific for disease.
     *
     * @param network          - Bayesian network for current disease.
     * @param providedSymptoms - symptoms collected directly from patient.
     * @param diseaseSymptoms  - all symptoms that are connected with certain disease.
     */
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

    /**
     * Sets the node name, and appends states.
     *
     * @param node
     * @param disease
     */
    private void initializeNode(ProbabilisticNode node, String disease) {
        node.setName(disease);
        node.appendState(STATE_POSITIVE);
        node.appendState(STATE_NEGATIVE);
    }

    public void setListener(HandlerProgressListener listener) {
        this.listener = listener;
    }

    public static void main(String[] args) throws FileNotFoundException, InvalidParentException {
        List<String> symptoms = new ArrayList<>();
        symptoms.add("eye_redness");
        symptoms.add("pain_in_eye");
        symptoms.add("cough");
        symptoms.add("nasal_congestion");
        symptoms.add("fever");
        symptoms.add("itchiness_of_eye");
        symptoms.add("swollen_eye");
        symptoms.add("white_discharge_from_eye");
        symptoms.add("lacrimation");
        symptoms.add("symptoms_of_eye");

        BayesInferenceHandler handler = new BayesInferenceHandler();
        Map<String, Float> diseaseProbability = handler.createNodes(symptoms);
        for (Map.Entry<String, Float> entry : diseaseProbability.entrySet()) {
            System.out.println(entry.getKey() + " | " + entry.getValue());
        }
    }
}
