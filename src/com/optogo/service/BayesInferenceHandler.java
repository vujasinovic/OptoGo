package com.optogo.service;

import com.optogo.utils.enums.DiseaseName;
import com.optogo.utils.parse.DiseaseSymptomParser;
import unbbayes.io.NetIO;
import unbbayes.prs.Edge;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import unbbayes.prs.exception.InvalidParentException;
import unbbayes.util.extension.bn.inference.IInferenceAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class BayesInferenceHandler {
    public static void main(String[] args) throws FileNotFoundException, InvalidParentException {
        ProbabilisticNetwork network = new ProbabilisticNetwork("cataract");

        Map<String, Float> symptomsWithProbabilities = DiseaseSymptomParser.getSymptomsWithProbabilities("resources/disease_symptom.txt", DiseaseName.CATARACT);

        ProbabilisticNode cataract = new ProbabilisticNode();
        cataract.setName("cataract");
        cataract.appendState("y");
        cataract.appendState("n");
        network.addNode(cataract);
        PotentialTable probCataract = cataract.getProbabilityFunction();
        probCataract.addVariable(cataract);

        for (String s : symptomsWithProbabilities.keySet()) {
            ProbabilisticNode symptom = new ProbabilisticNode();
            symptom.setName(s);
            symptom.appendState("y");
            symptom.appendState("n");
            PotentialTable symptomTable = symptom.getProbabilityFunction();
            symptomTable.addVariable(symptom);

            symptomTable.setValue(0, 0.5f);
            symptomTable.setValue(1, 0.5f);
            network.addNode(symptom);

            network.addEdge(new Edge(symptom, cataract));
        }

        PotentialTableValueCalculator calculator = new PotentialTableValueCalculator();
        probCataract.setValues(calculator.calculate(symptomsWithProbabilities));

        IInferenceAlgorithm algorithm = new JunctionTreeAlgorithm();
        algorithm.setNetwork(network);
        algorithm.run();

        ProbabilisticNode factNode = (ProbabilisticNode) network.getNode("cataract");
        int stateIndex = 1;
        factNode.addFinding(stateIndex);

        try {
            network.updateEvidences();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //save to file
        new NetIO().save(new File("result" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".net"), network);
    }
}
