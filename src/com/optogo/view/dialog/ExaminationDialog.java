package com.optogo.view.dialog;

import com.optogo.controller.ExaminationController;
import com.optogo.model.Patient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ExaminationDialog extends Stage {
    private static final String SCENE_FXML = "fxml/examination.fxml";
    private static final String TITLE = "Medical Examination";

    private ExaminationController controller;

    private ExaminationDialog(Stage parent, Patient patient) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(SCENE_FXML));
        Scene scene = new Scene(loader.load());

        controller = loader.getController();

        setHeight(450);
        setWidth(700);

        setTitle(TITLE);
        setScene(scene);
        initOwner(parent);
        initModality(Modality.APPLICATION_MODAL);
    }

    public static ExaminationDialog create(Stage parent, Patient patient) {
        ExaminationDialog dialog = null;
        try {
            dialog = new ExaminationDialog(parent,  patient);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        dialog.showAndWait();
        return dialog;
    }


}
