package com.optogo.model;

import com.optogo.utils.enums.SymptomName;

import javax.persistence.*;

@Entity
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SymptomName name;

    public Symptom() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SymptomName getName() {
        return name;
    }

    public void setName(SymptomName name) {
        this.name = name;
    }
}
