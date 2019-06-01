package com.optogo.model;

import com.optogo.utils.enums.ProcedureName;

import javax.persistence.*;

@Entity
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProcedureName title;

    public Procedure() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcedureName getTitle() {
        return title;
    }

    public void setTitle(ProcedureName title) {
        this.title = title;
    }
}
