package com.optogo.controller;

import com.optogo.model.Patient;
import com.optogo.utils.enums.GenderType;
import com.optogo.utils.enums.MaritalStatus;
import com.optogo.view.dialog.PatientEditorDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class PatientListController {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public TableView<Patient> tblPatients;

    final ObservableList<Patient> patients = FXCollections.observableArrayList(
            new Patient("Imenko", "Prezimenko", GenderType.MALE, "Danila Kisa 44", LocalDate.of(1996, 12, 22), "00000000", "bb", MaritalStatus.NEVER_MARRIED),
            new Patient("Imenko1", "Prezimenko1", GenderType.FEMALE, "Danila Kisa 44a", LocalDate.of(1952, 1, 9), "012/111-545", "cc", MaritalStatus.DIVORCED),
            new Patient("Imenko2", "Prezimenko2", GenderType.MALE, "Danila Kisa 44b", LocalDate.of(1955, 3, 1), "025/122-222", "d", MaritalStatus.SEPARATED),
            new Patient("Imenko3", "Prezimenko3", GenderType.FEMALE, "Danila Kisa 44c", LocalDate.of(1990, 12, 15), "021/125-555", "e", MaritalStatus.MARRIED),
            new Patient("Gender", "Bender", GenderType.OTHER, "Danila Kisa 44 prva soba desno", LocalDate.of(1992, 1, 15), "020/545-545", "ii", MaritalStatus.WIDOWED)
    );

    private TableColumn firstNameCol;
    private TableColumn lastNameCol;
    private TableColumn dateOfBirthCol;

    public PatientListController() throws ParseException {
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

    public ObservableList<Patient> getPatients() {
        return patients;
    }
}
