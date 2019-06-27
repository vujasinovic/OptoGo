package com.optogo.view.dialog;

import com.optogo.controller.prediction.PredictionListController;
import com.optogo.controller.prediction.PredictionsCollection;
import com.optogo.controller.prediction.Selection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SelectPredictedConditionDialog extends Stage {
    private static final String SCENE_FXML = "fxml/prediction_list.fxml";
    private static final String TITLE = "Predicted Conditions";

    private PredictionListController controller;

    private SelectPredictedConditionDialog(Stage parent, List<String> providedSymptoms, PredictionsCollection predictionsCollection) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(SCENE_FXML));
        Scene scene = new Scene(loader.load());

        controller = loader.getController();
        controller.setPredictions(predictionsCollection);
        controller.setProvidedSymptoms(providedSymptoms);

        setTitle(TITLE);
        setWidth(750);
        setHeight(750);

        setScene(scene);
        initOwner(parent);
        initModality(Modality.APPLICATION_MODAL);
    }

    public Selection getSelected() {
        return controller.getSelected();
    }

    public static SelectPredictedConditionDialog create(Stage parent, List<String> providedSymptoms, PredictionsCollection predictions) {
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

    public List<String> getExtendedSymptoms() {
        return controller.getExtraSymptoms();
    }

}
