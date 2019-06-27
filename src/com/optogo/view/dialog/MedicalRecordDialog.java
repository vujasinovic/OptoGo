package com.optogo.view.dialog;

import com.optogo.controller.task.MedicalRecordController;
import com.optogo.model.Patient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MedicalRecordDialog extends Stage {
    private static final String SCENE_FXML = "fxml/medical_record.fxml";
    private static final String TITLE = "Medical Record - %s %s";

    private MedicalRecordController controller;

    public MedicalRecordDialog(Stage parent, Patient patient) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(SCENE_FXML));
        Scene scene = new Scene(loader.load());

        controller = loader.getController();
        controller.setPatient(patient);

        setHeight(400);
        setWidth(600);

        setTitle(String.format(TITLE, patient.getFirstName(), patient.getLastName()));
        setScene(scene);
        initOwner(parent);
        initModality(Modality.APPLICATION_MODAL);
    }

    public static MedicalRecordDialog create(Stage parent, Patient patient) {
        MedicalRecordDialog dialog = null;
        try {
            dialog = new MedicalRecordDialog(parent, patient);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        dialog.showAndWait();
        return dialog;
    }

}
