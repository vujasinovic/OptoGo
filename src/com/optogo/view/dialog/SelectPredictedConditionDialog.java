package com.optogo.view.dialog;

import com.optogo.controller.PredictionListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class SelectPredictedConditionDialog extends Stage {
    private static final String SCENE_FXML = "fxml/prediction_list.fxml";

    private PredictionListController controller;

    private SelectPredictedConditionDialog(Stage parent, Map<String, Float> predictions) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(SCENE_FXML));
        Scene scene = new Scene(loader.load());

        controller = loader.getController();
        controller.setPredictions(predictions);

        setMinWidth(550);
        setMinHeight(700);

        setScene(scene);
        initOwner(parent);
        initModality(Modality.APPLICATION_MODAL);
    }

    public String getSelected() {
        return controller.getSelected();
    }

    public static SelectPredictedConditionDialog create(Stage parent, Map<String, Float> predictions) {
        SelectPredictedConditionDialog dialog = null;
        try {
            dialog = new SelectPredictedConditionDialog(parent,  predictions);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        dialog.showAndWait();
        return dialog;
    }

}
