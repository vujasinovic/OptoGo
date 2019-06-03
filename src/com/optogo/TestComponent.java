package com.optogo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestComponent extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("fxml/examination.fxml"));
        Scene stage = new Scene(loader.load());

        primaryStage.setScene(stage);
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(700);
        primaryStage.show();
    }
}
