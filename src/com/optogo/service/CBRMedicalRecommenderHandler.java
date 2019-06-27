package com.optogo.service;

import com.optogo.model.Disease;
import com.optogo.model.MedicalPrescription;
import com.optogo.model.Patient;
import ucm.gaia.jcolibri.cbrcore.CBRQuery;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CBRMedicalRecommenderHandler {

    private CBRMedicalRecommender cbrMedicalRecommender;

    public CBRMedicalRecommenderHandler() {
        cbrMedicalRecommender = new CBRMedicalRecommender();
    }

    public List<MedicalPrescription> predict(Patient patient, Disease disease) throws ExecutionException {
        Collection<RetrievalResult> retrievalResults;
        cbrMedicalRecommender.configure();
        cbrMedicalRecommender.preCycle();

        MedicalPrescription medicalPrescription = new MedicalPrescription();
        medicalPrescription.setPatient(patient);
        medicalPrescription.setDisease(disease);

        CBRQuery query = new CBRQuery();
        query.setDescription(medicalPrescription);

        List<MedicalPrescription> medicalPrescriptions = new ArrayList<>();
        retrievalResults = cbrMedicalRecommender.getResults(query);
        for (RetrievalResult retrievalResult : retrievalResults) {
            medicalPrescriptions.add((MedicalPrescription) retrievalResult.get_case().getDescription());
        }

        return medicalPrescriptions;
    }

}
