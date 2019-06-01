package com.optogo.model;

import com.optogo.utils.enums.ProcedureEnum;

import javax.persistence.*;

@Entity
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProcedureEnum title;

    public Procedure() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcedureEnum getTitle() {
        return title;
    }

    public void setTitle(ProcedureEnum title) {
        this.title = title;
    }
}
