package com.optogo.service;

import unbbayes.io.NetIO;
import unbbayes.prs.Node;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BayesInferenceHandlerUtilities implements MapActions<String, Float>{

    public static final String FILE_EXTENSION = ".net";
    public static final String PATH = "results/";

    public static void print(ProbabilisticNetwork network) {
        for (Node node : network.getNodes()) {
            System.out.println("*" + node.getName() + "*");
            for (int i = 0; i < node.getStatesSize(); i++) {
                System.out.println(node.getStateAt(i) + ": " + ((ProbabilisticNode) node).getMarginalAt(i));
            }
        }
    }

    public static void saveFile(ProbabilisticNetwork network, String diseaseName) throws FileNotFoundException {
        new NetIO().save(new File(PATH + diseaseName + FILE_EXTENSION), network);
    }

    public static ProbabilisticNetwork loadFile(String name) throws IOException {
        return (ProbabilisticNetwork)(new NetIO()).load(new File(PATH + name + FILE_EXTENSION));
    }

    @Override
    public Map<String, Float> sortByKeysAscending(Map<String, Float> mapToSort) {
        return mapToSort.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    @Override
    public Map<String, Float> sortByKeysDescending(Map<String, Float> mapToSort) {
        return mapToSort.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    @Override
    public  Map<String, Float> sortByValueAscending(Map<String, Float> mapToSort) {
        return mapToSort.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    @Override
    public Map<String, Float> sortByValueDescending(Map<String, Float> mapToSort) {
        return mapToSort.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
