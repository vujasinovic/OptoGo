package com.optogo.controller;

import com.optogo.model.Patient;
import javafx.scene.control.Label;

public class PatientInfoController {

    public Label txtFirstName;
    public Label txtLastName;
    public Label txtGender;
    public Label txtAddress;
    public Label txtDateOfBirth;
    public Label txtPhoneNumber;
    public Label txtCity;
    public Label txtMaritialStatus;

    public void set(Patient patient) {
        txtFirstName.setText(patient.getFirstName());
        txtLastName.setText(patient.getLastName());
        txtGender.setText(patient.getGender().name());
        txtAddress.setText(patient.getAddress());
        txtDateOfBirth.setText(patient.getDateOfBirth().toString());
        txtPhoneNumber.setText(patient.getPhoneNumber());
        txtCity.setText(patient.getCity());
        txtMaritialStatus.setText(patient.getMaritalStatus().name());
    }

}
