package com.optogo.view.scene;

import com.optogo.controller.PatientListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class PatientListScene extends Scene {
    private static final String FXML = "fxml/patient_list.fxml";

    private PatientListController controller;

    private PatientListScene(Parent root) {
        super(root);
    }

    public PatientListController getController() {
        return controller;
    }

    public static PatientListScene create() {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(FXML));
        PatientListScene patientListScene = null;
        try {
            patientListScene = new PatientListScene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        patientListScene.controller = loader.getController();
        return patientListScene;
    }

}
