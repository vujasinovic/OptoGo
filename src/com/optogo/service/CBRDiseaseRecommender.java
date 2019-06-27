package com.optogo.service;

import com.optogo.model.*;
import com.optogo.utils.TableSimilarity;
import com.optogo.utils.enums.GenderType;
import com.optogo.utils.parse.CsvConnector;
import ucm.gaia.jcolibri.casebase.LinealCaseBase;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.*;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CBRDiseaseRecommender implements StandardCBRApplication {

    Connector _connector;  /** Connector object */
    CBRCaseBase _caseBase;  /** CaseBase object */

    NNConfig simConfig;

    public Collection<RetrievalResult> getResult() {
        return result;
    }

    public void setResult(Collection<RetrievalResult> result) {
        this.result = result;
    }

    /** KNN configuration */

    private Collection<RetrievalResult> result = new ArrayList<>();

    public void configure() throws ExecutionException {
        _connector =  new CsvConnector();

        _caseBase = new LinealCaseBase();  // Create a Lineal case base for in-memory organization

        simConfig = new NNConfig(); // KNN configuration
        simConfig.setDescriptionSimFunction(new Average());  // global similarity function = average

        simConfig.addMapping(new Attribute("patient", Examination.class), new Average());
        TableSimilarity genderSimilarity = new TableSimilarity((Arrays.asList(new Enum[]{GenderType.MALE, GenderType.FEMALE, GenderType.OTHER})));
        genderSimilarity.setSimilarity(GenderType.MALE, GenderType.FEMALE, .85);
        genderSimilarity.setSimilarity(GenderType.MALE, GenderType.OTHER, .7);
        genderSimilarity.setSimilarity(GenderType.FEMALE, GenderType.OTHER, .7);
        simConfig.addMapping(new Attribute("gender", Patient.class), genderSimilarity);
        //simConfig.addMapping(new Attribute("dateOfBirth", Patient.class), new Average());
        simConfig.addMapping(new Attribute("race", Patient.class), new Equal());
        simConfig.addMapping(new Attribute("symptoms", Examination.class), new Average());

        // Equal - returns 1 if both individuals are equal, otherwise returns 0
        // Interval - returns the similarity of two number inside an interval: sim(x,y) = 1-(|x-y|/interval)
        // Threshold - returns 1 if the difference between two numbers is less than a threshold, 0 in the other case
        // EqualsStringIgnoreCase - returns 1 if both String are the same despite case letters, 0 in the other case
        // MaxString - returns a similarity value depending of the biggest substring that belong to both strings
        // EnumDistance - returns the similarity of two enum values as the their distance: sim(x,y) = |ord(x) - ord(y)|
        // EnumCyclicDistance - computes the similarity between two enum values as their cyclic distance
        // Table - uses a table to obtain the similarity between two values. Allowed values are Strings or Enums. The table is read from a text file.
        // TableSimilarity(List<String> values).setSimilarity(value1,value2,similarity)
    }

    public void cycle(CBRQuery query) throws ExecutionException {
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
        eval = SelectCases.selectTopKRR(eval, 5);
        System.out.println("Retrieved cases:");
        for (RetrievalResult nse : eval)
            System.out.println(nse.get_case().getDescription() + " -> " + nse.getEval());
        result = eval;
    }

    public void postCycle() throws ExecutionException {

    }

    public CBRCaseBase preCycle() throws ExecutionException {
        _caseBase.init(_connector);
        java.util.Collection<CBRCase> cases = _caseBase.getCases();
        for (CBRCase c : cases)
            System.out.println(c.getDescription());
        return _caseBase;
    }
}

