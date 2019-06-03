package com.optogo.view.dialog;

import com.optogo.controller.OptionSelectionListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RecomendedSymptomSelectionDialog extends Stage {
    private static final String SCENE_FXML = "fxml/option_select_list.fxml";
    private final OptionSelectionListController controller;

    private RecomendedSymptomSelectionDialog(Stage parent, List<String> symptoms) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(SCENE_FXML));
        Scene scene = new Scene(loader.load());

        controller = loader.getController();
        controller.setOptions(symptoms);

        setScene(scene);
        initOwner(parent);

        initModality(Modality.APPLICATION_MODAL);
    }

    public List<String> getSelected() {
        return controller.getSelected();
    }

    public static RecomendedSymptomSelectionDialog create(Stage parent, List<String> symptoms) {
        RecomendedSymptomSelectionDialog dialog;
        try {
            dialog = new RecomendedSymptomSelectionDialog(parent, symptoms);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        dialog.showAndWait();
        return dialog;
    }

}
