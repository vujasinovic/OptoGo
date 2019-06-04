package com.optogo.view.dialog;

import com.optogo.controller.PredictionListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SelectPredictedConditionDialog extends Stage {
    private static final String SCENE_FXML = "fxml/prediction_list.fxml";
    private static final String TITLE = "Predicted Conditions";

    private PredictionListController controller;

    private SelectPredictedConditionDialog(Stage parent, List<String> providedSymptoms, Map<String, Float> predictions) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(SCENE_FXML));
        Scene scene = new Scene(loader.load());

        controller = loader.getController();
        controller.setPredictions(predictions);
        controller.setProvidedSymptoms(providedSymptoms);

        setTitle(TITLE);
        setWidth(550);
        setHeight(700);


        setScene(scene);
        initOwner(parent);
        initModality(Modality.APPLICATION_MODAL);
    }

    public String getSelected() {
        return controller.getSelected();
    }

    public static SelectPredictedConditionDialog create(Stage parent, List<String> providedSymptoms, Map<String, Float> predictions) {
        SelectPredictedConditionDialog dialog = null;
        try {
            dialog = new SelectPredictedConditionDialog(parent, providedSymptoms, predictions);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        dialog.showAndWait();
        return dialog;
    }

}
