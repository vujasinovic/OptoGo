package com.optogo.utils;

import com.optogo.model.Disease;
import com.optogo.repository.impl.DiseaseRepository;
import com.optogo.utils.enums.DiseaseName;

public class InsertDiseases {

    public static void main(String[] args) {
        DiseaseName[] names = DiseaseName.class.getEnumConstants();
        DiseaseRepository repository = new DiseaseRepository();
        for (int i = 0; i < names.length; i++) {
            Disease disease = new Disease();
            disease.setName(names[i]);
            repository.save(disease);
        }
    }

}
