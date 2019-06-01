package com.optogo.view;

import com.optogo.view.scene.PatientListScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestPatientList extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(PatientListScene.create());
        stage.show();
    }

}
