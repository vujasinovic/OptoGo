package com.optogo.view.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainScene extends Scene {
    private static final String FXML = "fxml/main.fxml";

    public MainScene(Parent root) {
        super(root);
    }

    public static MainScene create() {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(FXML));
        MainScene mainScene = null;
        try {
            mainScene = new MainScene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mainScene;
    }

}
