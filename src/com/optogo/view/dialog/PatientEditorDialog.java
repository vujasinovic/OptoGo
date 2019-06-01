package com.optogo.view.dialog;

import com.optogo.controller.PatientController;
import com.optogo.model.Patient;
import com.optogo.view.scene.PatientEditorScene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PatientEditorDialog extends Stage {
    private PatientEditorScene scene;

    public PatientEditorDialog(Patient patient, Stage parent) {
        scene = PatientEditorScene.create(patient);

        setScene(scene);
        initOwner(parent);
        initModality(Modality.APPLICATION_MODAL);
    }

    public PatientController getController() {
        return scene.getController();
    }

}
