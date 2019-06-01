package com.optogo.model;

import com.optogo.utils.enums.MedicationEnum;

import javax.persistence.*;

@Entity
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MedicationEnum name;

    public Medication() {
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
