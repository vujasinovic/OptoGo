package com.optogo.controller;

import com.optogo.model.Patient;
import com.optogo.repository.impl.PatientRepository;
import com.optogo.view.dialog.PatientEditorDialog;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;

public class PatientListController implements ListChangeListener<Patient> {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public TableView<Patient> tblPatients;
    private PatientRepository repository;

    final ObservableList<Patient> patients;

    private TableColumn firstNameCol;
    private TableColumn lastNameCol;
    private TableColumn dateOfBirthCol;

    public PatientListController() {
        repository = new PatientRepository();
        patients = FXCollections.observableArrayList(repository.findAll());
        patients.addListener(this);
    }

    public void initialize() {
        setupColumns();

        tblPatients.setItems(patients);
    }

    private void setupColumns() {
        firstNameCol = tblPatients.getColumns().get(0);
        lastNameCol = tblPatients.getColumns().get(1);
        dateOfBirthCol = tblPatients.getColumns().get(2);

        firstNameCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("lastName"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("dateOfBirth"));
    }

    public void tblClicked(MouseEvent mouseEvent) {
        Patient clickedPatient = tblPatients.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 1) {
        } else {
            Stage stage = (Stage) ((Control) mouseEvent.getSource()).getScene().getWindow();
            PatientEditorDialog patientEditorDialog = new PatientEditorDialog(clickedPatient, stage);
            patientEditorDialog.showAndWait();
            if (!patientEditorDialog.getController().isCanceled()) {
                patients.set(patients.indexOf(clickedPatient), patientEditorDialog.getController().getPatient());
            }
        }
    }

    public ObservableList<Patient> getModel() {
        return patients;
    }

    public Patient getSelected() {
        int i = tblPatients.getSelectionModel().getSelectedIndex();
        if (i >= 0 && i < patients.size()) {
            return patients.get(i);
        } else {
            return null;
        }
    }

    public int getSelectedIndex() {
        return tblPatients.getSelectionModel().getSelectedIndex();
    }

    public ObservableList<Patient> getPatients() {
        return patients;
    }

    @Override
    public void onChanged(Change change) {
        change.next();
        if (change.wasReplaced()) {
            Patient patient = patients.get(change.getFrom());
            if(patient != null)
                repository.update(patient);
        } else if (change.wasAdded()) {
            repository.save(patients.get(change.getFrom()));
        } else if (change.wasRemoved()) {
            repository.delete((Patient) change.getRemoved().get(0));
        }
    }

}
