package com.optogo.model;

import com.optogo.utils.enums.DiseaseEnum;

public class Disease {
    private Long id;

    //TODO use @Enumerated(EnumType.STRING)
    private DiseaseEnum name;

    public Disease(Long id, DiseaseEnum name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiseaseEnum getName() {
        return name;
    }

    public void setName(DiseaseEnum name) {
        this.name = name;
    }
}
