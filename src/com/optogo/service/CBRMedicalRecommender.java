package com.optogo.service;

import com.optogo.model.*;
import com.optogo.service.bayes.DiseaseSimilarityCalculator;
import com.optogo.utils.TableSimilarity;
import com.optogo.utils.enums.DiseaseName;
import com.optogo.utils.enums.GenderType;
import com.optogo.utils.enums.Race;
import com.optogo.utils.parse.CsvConnector;
import ucm.gaia.jcolibri.casebase.LinealCaseBase;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.*;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CBRMedicalRecommender implements StandardCBRApplication {

    /**
     * Connector object
     */
    CBRCaseBase _caseBase;

    /**
     * CaseBase object
     */
    NNConfig simConfig;

    /**
     * KNN configuration
     */
    Connector _connector;

    public void configure() {
        _connector = new CsvConnector();

        _caseBase = new LinealCaseBase();  // Create a Lineal case base for in-memory organization

        simConfig = new NNConfig(); // KNN configuration
        simConfig.setDescriptionSimFunction(new Average());  // global similarity function = average

        simConfig.addMapping(new Attribute("patient", MedicalPrescription.class), new Average());

        TableSimilarity genderSimilarity = new TableSimilarity((Arrays.asList(new Enum[]{GenderType.MALE, GenderType.FEMALE, GenderType.OTHER})));
        genderSimilarity.setSimilarity(GenderType.MALE, GenderType.FEMALE, .85);
        genderSimilarity.setSimilarity(GenderType.MALE, GenderType.OTHER, .7);
        genderSimilarity.setSimilarity(GenderType.FEMALE, GenderType.OTHER, .7);

        simConfig.addMapping(new Attribute("gender", Patient.class), genderSimilarity);

        TableSimilarity raceSimilarity = new TableSimilarity((Arrays.asList(Race.class.getEnumConstants())));
        raceSimilarity.setSimilarity(Race.WHITE, Race.BLACK, .98);
        raceSimilarity.setSimilarity(Race.WHITE, Race.HISPANIC, .99);
        raceSimilarity.setSimilarity(Race.WHITE, Race.OTHER, .95);
        raceSimilarity.setSimilarity(Race.BLACK, Race.HISPANIC, .97);
        raceSimilarity.setSimilarity(Race.BLACK, Race.OTHER, .95);
        raceSimilarity.setSimilarity(Race.HISPANIC, Race.OTHER, .95);

        simConfig.addMapping(new Attribute("race", Patient.class), raceSimilarity);

        simConfig.addMapping(new Attribute("disease", MedicalPrescription.class), new Average());

        DiseaseName[] diseaseNames = DiseaseName.class.getEnumConstants();
        TableSimilarity diseaseSimilarity = new TableSimilarity(Arrays.asList(diseaseNames));
        for (int i = 0; i < DiseaseName.class.getEnumConstants().length; i++) {
            for (int j = 1; j < DiseaseName.class.getEnumConstants().length; j++) {
                try {
                    diseaseSimilarity.setSimilarity(diseaseNames[i], diseaseNames[j],
                            DiseaseSimilarityCalculator.getSimilarity(diseaseNames[i], diseaseNames[j]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        simConfig.addMapping(new Attribute("name", Disease.class), diseaseSimilarity);

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
    }

    public Collection<RetrievalResult> getResults(CBRQuery query) {
        Collection<RetrievalResult> res = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
        res = SelectCases.selectTopKRR(res, 5);
        return res;
    }

    public void postCycle() throws ExecutionException {

    }

    public CBRCaseBase preCycle() throws ExecutionException {
        _caseBase.init(_connector);
        java.util.Collection<CBRCase> cases = _caseBase.getCases();
//        for (CBRCase c : cases)
//            System.out.println("-----" + c.getDescription());
        return _caseBase;
    }

    public static void main(String[] args) {
        CBRMedicalRecommender medicalRecommender = new CBRMedicalRecommender();
        CBRMedicalRecommenderHandler medicalRecommenderHandler = new CBRMedicalRecommenderHandler();

        try {
            Patient patient = new Patient();
            Disease disease = new Disease();

            patient.setFirstName("Petar");
            patient.setLastName("Petrovic");
            patient.setGender(GenderType.MALE);
            patient.setRace(Race.WHITE);
            patient.setDateOfBirth(LocalDate.of(1996, 10, 3));

            disease.setName(DiseaseName.CATARACT);

            List<MedicalPrescription> medicalPrescriptions  = medicalRecommenderHandler.predict(patient, disease);
            List<Medication> medications = new ArrayList<>();
            List<Procedure> procedures = new ArrayList<>();

            for (MedicalPrescription medicalPrescription: medicalPrescriptions) {
                medications.add(medicalPrescription.getMedication());
                procedures.add(medicalPrescription.getProcedure());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
