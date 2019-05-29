package com.optogo.model;

import java.util.List;

public class MedicalRecord {
    private Long id;

    private Patient patient;

    private List<Disease> diseases;

    private List<Procedure> procedures;

    public MedicalRecord(Long id, Patient patient, List<Disease> diseases, List<Procedure> procedures) {
        this.id = id;
        this.patient = patient;
        this.diseases = diseases;
        this.procedures = procedures;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }
}
