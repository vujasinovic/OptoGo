package com.optogo.utils.parse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;


import com.optogo.model.*;
import com.optogo.utils.enums.*;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.InitializingException;
import ucm.gaia.jcolibri.util.FileIO;

public class CsvConnector implements Connector {

    @Override
    public Collection<CBRCase> retrieveAllCases() {
        LinkedList<CBRCase> cases = new LinkedList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(FileIO.openFile("primer1.csv")));
            if (br == null)
                throw new Exception("Error opening file");

            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || (line.length() == 0))
                    continue;
                String[] values = line.split(";");

                CBRCase cbrCase = new CBRCase();

                MedicalPrescription medicalPrescription = new MedicalPrescription();
                Patient patient = new Patient();
                Disease disease = new Disease();
                Procedure procedure = new Procedure();
                Medication medication = new Medication();

                medicalPrescription.setId(Long.parseLong(values[0]));

                patient.setId(Long.parseLong(values[1]));
                patient.setFirstName(values[2]);
                patient.setLastName(values[3]);
                patient.setGender(GenderType.valueOf(values[4]));
                patient.setRace(Race.valueOf(values[5]));
                patient.setAddress(values[6]);
                patient.setDateOfBirth(LocalDate.parse(values[7]));
                patient.setPhoneNumber(values[8]);
                patient.setCity(values[9]);

                disease.setId(Long.parseLong(values[10]));
                disease.setName(DiseaseName.valueOf(values[11]));

                procedure.setId(Long.parseLong(values[12]));
                procedure.setTitle(ProcedureName.valueOf(values[13]));

                medication.setId(Long.parseLong(values[14]));
                medication.setName(MedicationName.valueOf(values[15]));

                medicalPrescription.setPatient(patient);
                medicalPrescription.setDisease(disease);
                medicalPrescription.setProcedure(procedure);
                medicalPrescription.setMedication(medication);

                cbrCase.setDescription(medicalPrescription);
                cases.add(cbrCase);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
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
