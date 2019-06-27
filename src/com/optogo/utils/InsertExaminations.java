package com.optogo.utils;

import com.optogo.model.Disease;
import com.optogo.model.Examination;
import com.optogo.model.Patient;
import com.optogo.model.Symptom;
import com.optogo.repository.impl.DiseaseRepository;
import com.optogo.repository.impl.ExaminationRepository;
import com.optogo.repository.impl.PatientRepository;
import com.optogo.repository.impl.SymptomRepository;
import com.optogo.utils.enums.DiseaseName;
import com.optogo.utils.enums.GenderType;
import com.optogo.utils.enums.Race;
import com.optogo.utils.enums.SymptomName;

import java.util.ArrayList;
import java.util.List;

public class InsertExaminations {
    public static void main(String[] args) {

        //problem zbog id-a simptoma i bolesti(ne postoje od ranije u bazi
        //ili dodati i ove simptome i bolesti u bazu ili koristiti vec unesene id-e
        ExaminationRepository examinationRepository = new ExaminationRepository();
        PatientRepository patientRepository = new PatientRepository();
        DiseaseRepository diseaseRepository = new DiseaseRepository();
        SymptomRepository symptomRepository = new SymptomRepository();
        Examination examination1 = new Examination();
        Patient patient1 = new Patient();
        patient1.setFirstName("Dragan");
        patient1.setLastName("Jovic");
        patient1.setGender(GenderType.MALE);
        patient1.setRace(Race.WHITE);
        examination1.setPatient(patient1);
        patientRepository.save(patient1);

        List<Symptom> symptoms1 = new ArrayList<>();

        Symptom symptom01 = new Symptom();
        symptom01.setName(SymptomName.SYMPTOMS_OF_EYE);
        symptoms1.add(symptom01);
        symptomRepository.save(symptom01);
        Symptom symptom02 = new Symptom();
        symptom02.setName(SymptomName.ABNORMAL_APPEARING_SKIN);
        symptoms1.add(symptom02);
        symptomRepository.save(symptom02);
        Symptom symptom03 = new Symptom();
        symptom03.setName(SymptomName.CLOUDY_EYE);
        symptoms1.add(symptom03);
        symptomRepository.save(symptom03);
        Symptom symptom04 = new Symptom();
        symptom04.setName(SymptomName.EYE_BURNS_OR_STINGS);
        symptoms1.add(symptom04);
        symptomRepository.save(symptom04);


        examination1.setSymptoms(symptoms1);

        Disease disease1 = new Disease();
        disease1.setName(DiseaseName.CATARACT);
        diseaseRepository.save(disease1);

        // EXAMINATION table not found
        examinationRepository.save(examination1);

        Examination examination2 = new Examination();
        examination1.setId((long)2);

        Patient patient2 = new Patient();
        patient2.setFirstName("Dragan");
        patient2.setLastName("Draganovic");
        patient2.setGender(GenderType.MALE);
        patient2.setRace(Race.BLACK);
        examination2.setPatient(patient2);
        patientRepository.save(patient2);

        List<Symptom> symptoms2 = new ArrayList<>();

        Symptom symptom11 = new Symptom();
        symptom11.setName(SymptomName.EYE_DEVIATION);
        symptoms2.add(symptom11);
        symptomRepository.save(symptom11);
        Symptom symptom12 = new Symptom();
        symptom12.setName(SymptomName.BACK_WEAKNESS);
        symptoms2.add(symptom12);
        symptomRepository.save(symptom12);
        Symptom symptom13 = new Symptom();
        symptom13.setName(SymptomName.CORYZA);
        symptoms2.add(symptom13);
        symptomRepository.save(symptom13);

        examination2.setSymptoms(symptoms2);

        Disease disease2 = new Disease();
        disease2.setName(DiseaseName.AMBLYOPIA);
        diseaseRepository.save(disease2);

        examinationRepository.save(examination2);
    }
}

