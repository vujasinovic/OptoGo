package com.optogo.utils;

import com.optogo.model.Symptom;
import com.optogo.repository.impl.SymptomRepository;
import com.optogo.utils.enums.SymptomName;

public class InsertSymptoms {

    public static void main(String[] args) {
        SymptomName[] names = SymptomName.class.getEnumConstants();
        SymptomRepository repository = new SymptomRepository();
        for (int i = 0; i < names.length; i++) {
            Symptom symptom = new Symptom();
            symptom.setName(names[i]);
            repository.save(symptom);
        }
    }

}
