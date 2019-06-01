package com.optogo.model;

import com.optogo.utils.enums.SymptomEnum;

import javax.persistence.*;

@Entity
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SymptomEnum name;

    public Symptom() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SymptomEnum getName() {
        return name;
    }

    public void setName(SymptomEnum name) {
        this.name = name;
    }
}
