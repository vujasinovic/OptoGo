package com.optogo.service.bayes;

import com.optogo.utils.parse.DiseaseProcedureParser;
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

public class ProcedureBayesianNetwork {
    public static final String DISEASE_PROCEDURE_FILEPATH = "resources/disease_procedure.txt";
    public static final float DISEASE_INIT_PROBABILITY = 0.5f;
    public static final String YES_STATE = "y";
    public static final String NO_STATE = "n";
    public static final int YES_INDEX = 0;
    public static final int NO_INDEX = 1;
    public static final String STATE_POSITIVE = "positive";
    public static final String STATE_NEGATIVE = "negative";

    private HandlerProgressListener listener;

    public Map<String, Float> createNodes(Map<String, Float> diseases) throws IOException, InvalidParentException {
        Set<String> procedures = DiseaseProcedureParser.getProcedures(DISEASE_PROCEDURE_FILEPATH);
        Map<String, Float> procedureProbability = new HashMap<>();

        int count = 0;
        for (String procedure : procedures) {
            if (listener != null)
                listener.progressUpdated(++count, procedures.size(), procedure);

            ProbabilisticNetwork network = new ProbabilisticNetwork(procedure);
            ProbabilisticNode procedureNode = new ProbabilisticNode();
            initializeNode(procedureNode, procedure);
            network.addNode(procedureNode);

            PotentialTable probProcedure = procedureNode.getProbabilityFunction();
            probProcedure.addVariable(procedureNode);

            Map<String, Float> diseasesWithProbabilities = DiseaseProcedureParser.getDiseasesWithProbabilities(DISEASE_PROCEDURE_FILEPATH, procedure);
            for (String d : diseasesWithProbabilities.keySet()) {
                Float dProp = diseases.get(d);
                if (dProp == null) {
                    diseasesWithProbabilities.put(d, 0f);
                } else {
                    Float pProp = diseasesWithProbabilities.get(d);
                    diseasesWithProbabilities.put(d, pProp * dProp);
                }
            }

            createDiseaseNodes(network, procedureNode, diseasesWithProbabilities.keySet());

            PotentialTableValueCalculator calculator = new PotentialTableValueCalculator();
            probProcedure.setValues(calculator.calculate(diseasesWithProbabilities));
            IInferenceAlgorithm algorithm = new JunctionTreeAlgorithm();
            algorithm.setNetwork(network);
            algorithm.run();
            setFindings(network, diseases.keySet(), diseasesWithProbabilities.keySet());

            try {
                network.updateEvidences();
            } catch (Exception e) {
                e.printStackTrace();
            }

            procedureProbability.put(procedure, procedureNode.getMarginalAt(YES_INDEX));
            saveFile(network, procedure);
        }
        BayesInferenceHandlerUtilities bayesInferenceHandlerUtilities = new BayesInferenceHandlerUtilities();
        return bayesInferenceHandlerUtilities.sortByValueAscending(procedureProbability);
    }

    public void createDiseaseNodes(ProbabilisticNetwork network, ProbabilisticNode procedureNode, Set<String> diseases) throws InvalidParentException {
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

            network.addEdge(new Edge(disease, procedureNode));
        }
    }

    private void setFindings(ProbabilisticNetwork network, Collection<String> providedDiseases, Set<String> procedureDiseases) {
        for (String s : procedureDiseases) {
            ProbabilisticNode factNode = (ProbabilisticNode) network.getNode(s);
            if (providedDiseases.contains(s)) {
                factNode.addFinding(YES_INDEX);
            } else {
                factNode.addFinding(NO_INDEX, false);
            }
        }
    }

    private void initializeNode(ProbabilisticNode node, String procedure) {
        node.setName(procedure);
        node.appendState(STATE_POSITIVE);
        node.appendState(STATE_NEGATIVE);
    }

    public void setListener(HandlerProgressListener listener) {
        this.listener = listener;
    }

}
