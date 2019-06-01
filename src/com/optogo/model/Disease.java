package com.optogo.model;

import com.optogo.utils.enums.DiseaseEnum;

import javax.persistence.*;

@Entity
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiseaseEnum name;

    public Disease() {
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
