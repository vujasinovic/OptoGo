package com.optogo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String TITLE = "OptoGo";
    public static final String VERSION = "v1.0";
    public static final String ROOT = "/com/optogo/sample.fxml";
    public static final String APP_ICON = "images/icons/eyes.png";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(ROOT));
        primaryStage.setTitle(String.format("%s %s", TITLE, VERSION));
        primaryStage.getIcons().add(new Image(APP_ICON));
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
