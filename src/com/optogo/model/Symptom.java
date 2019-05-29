package com.optogo.model;

import com.optogo.utils.enums.SymptomEnum;

public class Symptom {
    private Long id;

    private SymptomEnum name;

    public Symptom(Long id, SymptomEnum name) {
        this.id = id;
        this.name = name;
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
