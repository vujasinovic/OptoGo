package com.optogo.model;

import com.optogo.utils.enums.DiseaseName;

import javax.persistence.*;

@Entity
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiseaseName name;

    public Disease() {
    }

    public Disease(Long id, DiseaseName name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiseaseName getName() {
        return name;
    }

    public void setName(DiseaseName name) {
        this.name = name;
    }
}
