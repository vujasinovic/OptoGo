package com.optogo.view.dialog;

import com.optogo.controller.ConditionSearchController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ConditionSearchDialog extends Stage {
    private static final String SCENE_FXML = "fxml/condition_search.fxml";

    private ConditionSearchController controller;

    private ConditionSearchDialog(Stage parent) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(SCENE_FXML));
        Scene scene = new Scene(loader.load());

        controller = loader.getController();

        setScene(scene);
        initOwner(parent);
        initModality(Modality.APPLICATION_MODAL);
    }

    public String getSelected() {
        return controller.getSelected();
    }

    public static ConditionSearchDialog create(Stage parent) {
        ConditionSearchDialog dialog;
        try {
            dialog = new ConditionSearchDialog(parent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        dialog.showAndWait();
        return dialog;
    }

}
