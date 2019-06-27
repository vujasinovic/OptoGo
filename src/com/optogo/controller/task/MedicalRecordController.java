package com.optogo.controller.task;

import com.optogo.model.Examination;
import com.optogo.model.Patient;
import com.optogo.model.Symptom;
import com.optogo.repository.impl.PatientRepository;
import com.optogo.utils.StringFormatter;
import com.optogo.utils.enums.SymptomName;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class MedicalRecordController {
    public ListView<ExaminationItem> listExaminations;

    private Patient patient;

    @FXML
    private Label lblDate;
    @FXML
    private ListView<String> listSymptoms;
    @FXML
    private Label lblCondition;
    @FXML
    private Label lblMedication;
    @FXML
    private Label lblProcedure;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final PatientRepository repository;

    public MedicalRecordController() {
        repository = new PatientRepository();
    }

    public void initialize() {
    }

    public void setPatient(Patient patient) {
        this.patient = repository.update(patient);
        listExaminations.getItems()
                .addAll(patient.getExaminations().stream().map(ExaminationItem::new).collect(Collectors.toList()));
    }

    public void setExamination(Examination examination) {
        this.listSymptoms.getItems().clear();

        this.lblDate.setText(examination.getDate().format(dateTimeFormatter));
        this.listSymptoms.getItems().addAll(examination.getSymptoms()
                .stream()
                .map(Symptom::getName)
                .map(SymptomName::name)
                .map(StringFormatter::capitalizeWord).collect(Collectors.toList()));
        if (examination.getDisease() != null)
            this.lblCondition.setText(StringFormatter.capitalizeWord(examination.getDisease().getName().name()));
        if (examination.getMedication() != null)
            this.lblMedication.setText(StringFormatter.capitalizeWord(examination.getMedication().getName().name()));
        if (examination.getProcedure() != null)
            this.lblProcedure.setText(StringFormatter.capitalizeWord(examination.getProcedure().getTitle().name()));
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
