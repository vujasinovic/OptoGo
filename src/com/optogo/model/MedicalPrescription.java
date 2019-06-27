package com.optogo.model;

import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

public class MedicalPrescription implements CaseComponent {
    private Long id;

    private Patient patient;

    private Disease disease;

    private Procedure procedure;

    private Medication medication;

    public MedicalPrescription(Long id, Patient patient, Disease disease, Procedure procedure, Medication medication) {
        this.id = id;
        this.patient = patient;
        this.disease = disease;
        this.procedure = procedure;
        this.medication = medication;
    }

    public MedicalPrescription() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }

    public void setPatient(Patient patient) { this.patient = patient; }

    public Disease getDisease() { return disease; }

    public void setDisease(Disease disease) { this.disease = disease; }

    public Procedure getProcedure() { return procedure; }

    public void setProcedure(Procedure procedure) { this.procedure = procedure; }

    public Medication getMedication() { return medication; }

    public void setMedication(Medication medication) { this.medication = medication; }

    @Override
    public Attribute getIdAttribute() {
        return null;
    }

}
