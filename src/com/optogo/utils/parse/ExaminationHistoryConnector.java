package com.optogo.utils.parse;

import com.optogo.model.Examination;
import com.optogo.model.MedicalPrescription;
import com.optogo.model.Medication;
import com.optogo.model.Procedure;
import com.optogo.repository.impl.ExaminationRepository;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.InitializingException;

import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

public class ExaminationHistoryConnector implements Connector {

    @Override
    public Collection<CBRCase> retrieveAllCases() {
        LinkedList<CBRCase> cases = new LinkedList<>();

        ExaminationRepository examinationRepository = new ExaminationRepository();
        for (Examination examination : examinationRepository.findAll()) {

            if(examination.getProcedure().isEmpty())
                examination.getProcedure().add(null);
            if(examination.getMedication().isEmpty())
                examination.getMedication().add(null);

            for (Procedure procedure : examination.getProcedure()) {
                for (Medication medication : examination.getMedication()) {
                    CBRCase cbrCase = new CBRCase();
                    MedicalPrescription medicalPrescription = new MedicalPrescription();

                    medicalPrescription.setPatient(examination.getPatient());
                    medicalPrescription.setDisease(examination.getDisease());
                    medicalPrescription.setProcedure(procedure);
                    medicalPrescription.setMedication(medication);

                    cbrCase.setDescription(medicalPrescription);
                    cases.add(cbrCase);
                }
            }
        }

        return cases;
    }

    @Override
    public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter arg0) {
        return null;
    }

    @Override
    public void storeCases(Collection<CBRCase> arg0) {
    }

    @Override
    public void close() {
    }

    @Override
    public void deleteCases(Collection<CBRCase> arg0) {
    }

    @Override
    public void initFromXMLfile(URL arg0) throws InitializingException {
    }

}
