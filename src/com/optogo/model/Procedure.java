package com.optogo.model;

import com.optogo.utils.enums.ProcedureEnum;

public class Procedure {
    private Long id;

    //TODO use @Enumarated(EnumType.STRING) to store enum as a string in database
    private ProcedureEnum title;

    public Procedure(Long id, ProcedureEnum title) {
        this.id = id;
        this.title = title;
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
