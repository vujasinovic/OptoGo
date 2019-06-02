package com.optogo.controller;

import com.optogo.model.Patient;
import com.optogo.view.dialog.PatientEditorDialog;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private BorderPane patientList;

    @FXML
    private PatientListController patientListController;

    public void initialize() {
    }

    public void start(ActionEvent actionEvent) {
    }

    public void edit(ActionEvent actionEvent) {
        PatientEditorDialog dialog = PatientEditorDialog.edit(patientListController.getSelected(),
                (Stage) ((Control) actionEvent.getSource()).getScene().getWindow());

        if (!dialog.isCanceled()) {
            patientListController.getModel().set(patientListController.getSelectedIndex(), dialog.getController().getPatient());
        }
    }

    public void remove(ActionEvent actionEvent) {
        ObservableList<Patient> model = patientListController.getModel();
        if (patientListController.getSelectedIndex() >= 0 && patientListController.getSelectedIndex() < model.size())
            model.remove(patientListController.getSelectedIndex());
    }
}
