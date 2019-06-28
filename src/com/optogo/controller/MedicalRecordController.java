package com.optogo.controller;

import com.optogo.model.*;
import com.optogo.repository.impl.PatientRepository;
import com.optogo.utils.StringFormatter;
import com.optogo.utils.enums.MedicationName;
import com.optogo.utils.enums.ProcedureName;
import com.optogo.utils.enums.SymptomName;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class MedicalRecordController {
    public ListView<ExaminationItem> listExaminations;
    public GridPane contentPane;

    @FXML
    private ListView<String> listProcedures;
    @FXML
    private ListView<String> listMedications;

    private Patient patient;

    @FXML
    private Label lblDate;
    @FXML
    private ListView<String> listSymptoms;
    @FXML
    private Label lblCondition;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final PatientRepository repository;

    public MedicalRecordController() {
        repository = new PatientRepository();
    }

    public void initialize() {
        contentPane.setVisible(false);
    }

    public void setPatient(Patient patient) {
        this.patient = repository.findById(patient.getId());

        listExaminations.getItems()
                .addAll(patient.getExaminations().stream().map(ExaminationItem::new).collect(Collectors.toList()));
    }

    public void setExamination(Examination examination) {
        contentPane.setVisible(true);
        this.listSymptoms.getItems().clear();
        this.listMedications.getItems().clear();
        this.listProcedures.getItems().clear();

        this.listSymptoms.getItems().addAll(examination.getSymptoms()
                .stream()
                .map(Symptom::getName).map(SymptomName::name).map(StringFormatter::capitalizeWord)
                .collect(Collectors.toList()));

        this.listMedications.getItems().addAll(examination.getMedication()
                .stream()
                .map(Medication::getName).map(MedicationName::name).map(StringFormatter::capitalizeWord)
                .collect(Collectors.toList()));

        this.listProcedures.getItems().addAll(examination.getProcedure()
                .stream()
                .map(Procedure::getTitle).map(ProcedureName::name).map(StringFormatter::capitalizeWord)
                .collect(Collectors.toList()));

        this.lblDate.setText(examination.getDate().format(dateTimeFormatter));

        if (examination.getDisease() != null)
            this.lblCondition.setText(StringFormatter.capitalizeWord(examination.getDisease().getName().name()));
    }

    public void select(MouseEvent mouseEvent) {
        ExaminationItem selectedItem = listExaminations.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            setExamination(selectedItem.getExamination());
        }
    }

    public void close(ActionEvent actionEvent) {
        getStage(actionEvent).close();
    }

    private static class ExaminationItem {
        private Examination examination;

        public ExaminationItem(Examination examination) {
            this.examination = examination;
        }

        public Examination getExamination() {
            return examination;
        }

        @Override
        public String toString() {
            return examination.getDate().format(dateTimeFormatter);
        }
    }

    private Stage getStage(Event event) {
        return (Stage) ((Control) event.getSource()).getScene().getWindow();
    }

}
