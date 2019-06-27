package com.optogo.service.bayes;

import com.optogo.utils.parse.DiseaseMedicationParser;
import unbbayes.prs.Edge;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import unbbayes.prs.exception.InvalidParentException;
import unbbayes.util.extension.bn.inference.IInferenceAlgorithm;

import java.io.IOException;
import java.util.*;

import static com.optogo.service.bayes.BayesInferenceHandlerUtilities.saveFile;

public class MedicationBayesianNetwork {
    public static final String DISEASE_MEDICATION_FILEPATH = "resources/disease_medication.txt";
    public static final float DISEASE_INIT_PROBABILITY = 0.5f;
    public static final String YES_STATE = "y";
    public static final String NO_STATE = "n";
    public static final int YES_INDEX = 0;
    public static final int NO_INDEX = 1;
    public static final String STATE_POSITIVE = "positive";
    public static final String STATE_NEGATIVE = "negative";

    private HandlerProgressListener listener;

    public Map<String, Float> createNodes(Map<String, Float> diseases) throws IOException, InvalidParentException {
        Set<String> medications = DiseaseMedicationParser.getAllMedications(DISEASE_MEDICATION_FILEPATH);
        Map<String, Float> medicationProbability = new HashMap<>();

        int count = 0;
        for (String medication : medications) {
            if (listener != null)
                listener.progressUpdated(++count, medications.size(), medication);

            ProbabilisticNetwork network = new ProbabilisticNetwork(medication);
            ProbabilisticNode medicationNode = new ProbabilisticNode();
            initializeNode(medicationNode, medication);
            network.addNode(medicationNode);

            PotentialTable probMedication = medicationNode.getProbabilityFunction();
            probMedication.addVariable(medicationNode);

            Map<String, Float> diseasesWithProbabilities = DiseaseMedicationParser.getDiseasesWithProbabilities(DISEASE_MEDICATION_FILEPATH, medication);
            for (String d : diseasesWithProbabilities.keySet()) {
                Float dProp = diseases.get(d);
                if (dProp == null) {
                    diseasesWithProbabilities.put(d, 0f);
                } else {
                    Float pProp = diseasesWithProbabilities.get(d);
                    diseasesWithProbabilities.put(d, pProp * dProp);
                }
            }

            createDiseaseNodes(network, medicationNode, diseasesWithProbabilities.keySet());

            PotentialTableValueCalculator calculator = new PotentialTableValueCalculator();
            probMedication.setValues(calculator.calculate(diseasesWithProbabilities));

            IInferenceAlgorithm algorithm = new JunctionTreeAlgorithm();
            algorithm.setNetwork(network);
            algorithm.run();

            setFindings(network, diseases.keySet(), diseasesWithProbabilities.keySet());

            try {
                network.updateEvidences();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            print(network);

            medicationProbability.put(medication, medicationNode.getMarginalAt(YES_INDEX));
            saveFile(network, medication);
        }
        BayesInferenceHandlerUtilities bayesInferenceHandlerUtilities = new BayesInferenceHandlerUtilities();
        return bayesInferenceHandlerUtilities.sortByValueAscending(medicationProbability);
    }

    public void createDiseaseNodes(ProbabilisticNetwork network, ProbabilisticNode medicationNode, Set<String> diseases) throws InvalidParentException {
        for (String s : diseases) {
            ProbabilisticNode disease = new ProbabilisticNode();
            disease.setName(s);
            disease.appendState(YES_STATE);
            disease.appendState(NO_STATE);
            PotentialTable diseaseTable = disease.getProbabilityFunction();
            diseaseTable.addVariable(disease);

            diseaseTable.setValue(YES_INDEX, DISEASE_INIT_PROBABILITY);
            diseaseTable.setValue(NO_INDEX, DISEASE_INIT_PROBABILITY);
            network.addNode(disease);

            network.addEdge(new Edge(disease, medicationNode));
        }
    }

    private void setFindings(ProbabilisticNetwork network, Collection<String> providedDiseases, Set<String> medicationDiseases) {
        for (String s : medicationDiseases) {
            ProbabilisticNode factNode = (ProbabilisticNode) network.getNode(s);
            if (providedDiseases.contains(s)) {
                factNode.addFinding(YES_INDEX);
            } else {
                factNode.addFinding(NO_INDEX, false);
            }
        }
    }

    private void initializeNode(ProbabilisticNode node, String medication) {
        node.setName(medication);
        node.appendState(STATE_POSITIVE);
        node.appendState(STATE_NEGATIVE);
    }

    public void setListener(HandlerProgressListener listener) {
        this.listener = listener;
    }

}
