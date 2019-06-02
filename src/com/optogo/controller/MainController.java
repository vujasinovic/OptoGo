package com.optogo.controller;

import com.optogo.model.Patient;
import com.optogo.view.dialog.PatientEditorDialog;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class MainController {
    public TextFlow txtFlow;

    @FXML
    private BorderPane patientList;

    @FXML
    private PatientListController patientListController;

    public void initialize() {
        Font font = Font.font("Verdana", FontWeight.BOLD, 25);
        Text opto = new Text("Opto");
        opto.setFill(Color.CADETBLUE);
        opto.setFont(font);

        Text go = new Text("Go");
        go.setFont(font);
        go.setFill(Color.INDIANRED);

        txtFlow.getChildren().addAll(opto, go);
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

    public void add(ActionEvent actionEvent) {
        PatientEditorDialog dialog = PatientEditorDialog.create((Stage) ((Control) actionEvent.getSource()).getScene().getWindow());

        if (!dialog.isCanceled()) {
            patientListController.getModel().add(dialog.getController().getPatient());
        }

    }
}
