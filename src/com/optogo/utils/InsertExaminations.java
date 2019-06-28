package com.optogo.utils;

import com.optogo.model.Examination;
import com.optogo.model.Symptom;
import com.optogo.repository.impl.DiseaseRepository;
import com.optogo.repository.impl.ExaminationRepository;
import com.optogo.repository.impl.PatientRepository;
import com.optogo.repository.impl.SymptomRepository;
import com.optogo.utils.enums.SymptomName;

import java.time.LocalDateTime;
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

        Symptom s1 = symptomRepository.findByName(SymptomName.ABNORMAL_MOVEMENT_OF_EYELID);
        Symptom s2 = symptomRepository.findByName(SymptomName.PAIN_IN_EYE);
        Symptom s3 = symptomRepository.findByName(SymptomName.BLINDNESS);
        Symptom s4 = symptomRepository.findByName(SymptomName.ABNORMAL_BREATHING_SOUNDS);
        List<Symptom> symptoms = new ArrayList<>();
        symptoms.add(s1);
        symptoms.add(s2);
        symptoms.add(s3);
        symptoms.add(s4);


        Examination e1 = new Examination();
        e1.setPatient(patientRepository.findById(1L));
        e1.setSymptoms(symptoms);
        e1.setDate(LocalDateTime.now());
        e1.setDisease(diseaseRepository.findById(1L));


        Examination e2 = new Examination();
        e2.setPatient(patientRepository.findById(2L));
        e2.setSymptoms(symptoms);
        e2.setDate(LocalDateTime.now());
        e2.setDisease(diseaseRepository.findById(2L));

        examinationRepository.save(e1);
    }
}

