package com.optogo.controller.prediction;

import java.util.List;

public class Selection {
    private String disease;
    private List<String> medications;
    private List<String> procedures;

    public Selection(String disease, List<String> medications, List<String> procedures) {
        this.disease = disease;
        this.medications = medications;
        this.procedures = procedures;
    }

    public Selection() {
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<String> procedures) {
        this.procedures = procedures;
    }
}
