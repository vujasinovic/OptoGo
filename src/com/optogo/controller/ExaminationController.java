package com.optogo.controller;

import com.optogo.controller.prediction.Selection;
import com.optogo.controller.task.BayasInterfaceHandlerTask;
import com.optogo.model.Examination;
import com.optogo.model.Patient;
import com.optogo.controller.prediction.PredictionsCollection;
import com.optogo.repository.impl.*;
import com.optogo.utils.StringFormatter;
import com.optogo.utils.enums.DiseaseName;
import com.optogo.view.dialog.ConditionSearchDialog;
import com.optogo.view.dialog.SelectPredictedConditionDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ExaminationController {

    @FXML
    private Accordion medicationAccordion;
    @FXML
    private Accordion procedureAccordion;
    @FXML
    private ProgressBar pb;
    @FXML
    private Label lblProgress;
    @FXML
    private TitledPane proceduresPanel;
    @FXML
    private TitledPane medicationsPanel;
    @FXML
    private BorderPane medicationSelection;
    @FXML
    private BorderPane procedureSelection;

    @FXML
    private BorderPane symptomSelection;

    @FXML
    private Label txtCondition;
    @FXML
    private Accordion diagnosisAccordion;
    @FXML
    private TitledPane diagnosisPane;
    @FXML
    private Accordion leftAccordion;
    @FXML
    private TitledPane symptomsPanel;
    @FXML
    private GridPane diagnosisGrid;

    @FXML
    private SelectionController symptomSelectionController;
    @FXML
    private SelectionController procedureSelectionController;
    @FXML
    private SelectionController medicationSelectionController;

    private final ExaminationRepository examinationRepository;
    private final DiseaseRepository diseaseRepository;
    private final SymptomRepository symptomRepository;
    private final PatientRepository patientRepository;
    private final ProcedureRepository procedureRepository;
    private final MedicationRepository medicationRepository;

    private BayasInterfaceHandlerTask bayesTask;

    private Patient patient;

    public ExaminationController() {
        examinationRepository = new ExaminationRepository();
        diseaseRepository = new DiseaseRepository();
        symptomRepository = new SymptomRepository();
        patientRepository = new PatientRepository();
        procedureRepository = new ProcedureRepository();
        medicationRepository = new MedicationRepository();
    }

    public void initialize() {
        leftAccordion.setExpandedPane(symptomsPanel);
        symptomsPanel.setCollapsible(false);

        diagnosisAccordion.setExpandedPane(diagnosisPane);
        diagnosisPane.setCollapsible(false);

        medicationAccordion.setExpandedPane(medicationsPanel);
        medicationsPanel.setCollapsible(false);

        procedureAccordion.setExpandedPane(proceduresPanel);
        proceduresPanel.setCollapsible(false);

        symptomSelectionController.addItems(symptomRepository.findAll().stream()
                .map(s -> StringFormatter.capitalizeWord(s.getName().name())).collect(Collectors.toList()));
        procedureSelectionController.addItems(procedureRepository.findAll().stream()
                .map(s -> StringFormatter.capitalizeWord(s.getTitle().name())).collect(Collectors.toList()));
        medicationSelectionController.addItems(medicationRepository.findAll().stream()
                .map(s -> StringFormatter.capitalizeWord(s.getName().name())).collect(Collectors.toList()));
    }

    public void select(ActionEvent actionEvent) {
        String selection = ConditionSearchDialog.create(getStage(actionEvent)).getSelected();
        if (selection != null)
            txtCondition.setText(selection);
    }

    private Stage getStage(Event event) {
        return (Stage) ((Control) event.getSource()).getScene().getWindow();
    }

    public void predict(ActionEvent actionEvent) {
        bayesTask = new BayasInterfaceHandlerTask(symptomSelectionController.getSelected());
        bayesTask.setOnSucceeded(workerStateEvent -> {
            predictionCompleted(actionEvent);
        });
        bayesTask.setHandlerProgressListener((current, max, message) -> {
            Platform.runLater(() -> {
                pb.setProgress((current * 1d / max));
                lblProgress.setText("Calculating: " + StringFormatter.capitalizeWord(message));
            });
        });
        new Thread(bayesTask).start();

    }

    private void predictionCompleted(Event actionEvent) {
        PredictionsCollection predictions = null;
        try {
            predictions = bayesTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }

        lblProgress.setText("Completed");

        SelectPredictedConditionDialog selectPredictedConditionDialog = SelectPredictedConditionDialog
                .create(getStage(actionEvent), symptomSelectionController.getSelected(), predictions);

        Selection selected = selectPredictedConditionDialog.getSelected();
        txtCondition.setText(selected.getDisease());
        medicationSelectionController.selectAll(selected.getMedications());
        procedureSelectionController.selectAll(selected.getProcedures());

        symptomSelectionController.selectAll(selectPredictedConditionDialog.getExtendedSymptoms());
    }

    public void save(ActionEvent actionEvent) {
        Examination examination = new Examination();

        String disease = txtCondition.getText();
        if (disease != null && !disease.trim().isEmpty())
            examination.setDisease(diseaseRepository.findByName(
                    DiseaseName.valueOf(StringFormatter.uderscoredLowerCase(disease).toUpperCase())));

        examination.setSymptoms(symptomRepository.findAllByName(
                symptomSelectionController.getSelected().stream().map(StringFormatter::uderscoredLowerCase)
                        .collect(Collectors.toList())));

        examination.setPatient(patient);
        examination.setDate(LocalDateTime.now());

        examinationRepository.save(examination);

        getStage(actionEvent).close();
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void close(ActionEvent actionEvent) {
        getStage(actionEvent).close();
    }

}
