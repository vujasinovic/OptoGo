package com.optogo.model;

import com.optogo.utils.enums.MedicationEnum;

public class Medication {
    private Long id;

    //TODO use @Enumarated(EnumType.STRING) to store enum as a string in database
    private MedicationEnum name;

    public Medication(Long id, MedicationEnum name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedicationEnum getName() {
        return name;
    }

    public void setName(MedicationEnum name) {
        this.name = name;
    }
}
