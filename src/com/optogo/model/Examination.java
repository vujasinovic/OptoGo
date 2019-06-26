package com.optogo.model;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Collection;

@Entity
public class Examination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Procedure procedure;

    @ManyToOne
    private Disease disease;

    @ManyToMany
    private Collection<Symptom> symptoms;

    @ManyToOne
    private Patient patient;

    private LocalTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Collection<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Collection<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalTime getDate() {
        return date;
    }

    public void setDate(LocalTime date) {
        this.date = date;
    }

}
