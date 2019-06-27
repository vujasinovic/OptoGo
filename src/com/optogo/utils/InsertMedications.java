package com.optogo.utils;

import com.optogo.model.Medication;
import com.optogo.repository.impl.MedicationRepository;
import com.optogo.utils.enums.MedicationName;

public class InsertMedications {

    public static void main(String[] args) {
        MedicationName[] names = MedicationName.class.getEnumConstants();
        MedicationRepository repository = new MedicationRepository();
        for (int i = 0; i < names.length; i++) {
            Medication med = new Medication();
            med.setName(names[i]);
            repository.save(med);
        }
    }

}
