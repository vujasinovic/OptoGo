package com.optogo.view.scene;

import com.optogo.controller.PatientController;
import com.optogo.model.Patient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class PatientEditorScene extends Scene {
    private static final String FXML = "fxml/patient.fxml";

    private PatientController controller;

    public PatientEditorScene(Parent root, Patient patient) throws IOException {
        super(root);
    }

    public PatientController getController() {
        return controller;
    }

    public static PatientEditorScene create(Patient patient) {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(FXML));
        PatientEditorScene patientEditorScene = null;
        try {
            patientEditorScene = new PatientEditorScene(loader.load(), patient);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        PatientController controller = loader.getController();
        controller.setPatient(patient);
        patientEditorScene.controller = controller;

        return patientEditorScene;
    }

}
