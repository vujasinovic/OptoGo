package com.optogo.view.dialog;

import com.optogo.controller.GraphDisplayController;
import com.optogo.graphics.Graph;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphDisplayDialog extends Stage {
    private static final String SCENE_FXML = "fxml/graph_display.fxml";
    private static final String TITLE = "Visualization";

    private GraphDisplayController controller;

    public GraphDisplayDialog(Stage parent, Graph graph) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(SCENE_FXML));
        Scene scene = new Scene(loader.load());

        controller = loader.getController();
        controller.show(graph);

        setHeight(800);
        setWidth(1250);

        setTitle(TITLE);
        setScene(scene);
        initOwner(parent);
        initModality(Modality.APPLICATION_MODAL);
    }

    public static GraphDisplayDialog create(Stage parent, Graph graph) {
        GraphDisplayDialog dialog = null;
        try {
            dialog = new GraphDisplayDialog(parent, graph);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        dialog.showAndWait();
        return dialog;
    }

}
