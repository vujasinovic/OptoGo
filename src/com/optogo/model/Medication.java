package com.optogo.model;

import com.optogo.utils.enums.MedicationName;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

import javax.persistence.*;

@Entity
public class Medication implements CaseComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MedicationName name;

    public Medication() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedicationName getName() {
        return name;
    }

    public void setName(MedicationName name) {
        this.name = name;
    }

    @Override
    public Attribute getIdAttribute() {
        return null;
    }
}
