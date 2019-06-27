package com.optogo.controller;

import com.optogo.model.Patient;
import com.optogo.utils.enums.GenderType;
import com.optogo.utils.enums.MaritalStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PatientController {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private ComboBox cbGender;
    @FXML
    private TextField txtAdress;
    @FXML
    private DatePicker dpDateOfBirth;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private ComboBox cbMaritialStatus;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private Long id;

    private Patient patient;

    private boolean canceled = true;

    ObservableList<GenderType> genders =
            FXCollections.observableArrayList(GenderType.FEMALE, GenderType.MALE, GenderType.OTHER);

    ObservableList<MaritalStatus> maritialStatuses = FXCollections.observableArrayList(MaritalStatus.NEVER_MARRIED,
            MaritalStatus.MARRIED, MaritalStatus.DIVORCED, MaritalStatus.SEPARATED, MaritalStatus.WIDOWED);

    public void initialize() {
        cbGender.setItems(genders);
        cbMaritialStatus.setItems(maritialStatuses);
    }

    public void save(ActionEvent actionEvent) {
        patient = new Patient();

        patient.setId(this.id);
        patient.setFirstName(txtFirstName.getText().trim());
        patient.setLastName(txtLastName.getText().trim());
        patient.setGender((GenderType) cbGender.getSelectionModel().getSelectedItem());
        patient.setAddress(txtAdress.getText().trim());
        patient.setDateOfBirth(dpDateOfBirth.getValue());
        patient.setPhoneNumber(txtPhoneNumber.getText().trim());
        patient.setMaritalStatus((MaritalStatus) cbMaritialStatus.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Control) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Control) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        if (patient != null) {
            this.id = patient.getId();
            txtFirstName.setText(patient.getFirstName());
            txtLastName.setText(patient.getLastName());
            cbGender.getSelectionModel().select(patient.getGender());
            txtAdress.setText(patient.getAddress());
            dpDateOfBirth.setValue(patient.getDateOfBirth());
            txtPhoneNumber.setText(patient.getPhoneNumber());
            cbMaritialStatus.getSelectionModel().select(patient.getMaritalStatus());
        }
    }

    public boolean isCanceled() {
        return canceled;
    }
}
