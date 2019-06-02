package com.optogo;

import com.optogo.view.scene.MainScene;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String TITLE = "OptoGo";
    public static final String VERSION = "v1.0";
    public static final String APP_ICON = "eyes.png";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(String.format("%s %s", TITLE, VERSION));
        primaryStage.getIcons().add(new Image(APP_ICON));
        primaryStage.setScene(MainScene.create());
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(1000);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
