package com.optogo.view.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class PredictedCondition extends HBox {
    private static String SOURCE = "fxml/predicted_condition.fxml";

    @FXML
    private Label lblProgress;

    @FXML
    private RadioButton radioBtn;

    @FXML
    private ProgressBar progressBar;

    private String name;
    private double value;

    public PredictedCondition(String name, double value) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource(SOURCE));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.name = name;
        this.value = value;

        init();
    }

    private void init() {
        progressBar.setProgress(value);
        radioBtn.setText(name);
        lblProgress.setText(String.format("%.2f", value * 100)+ "%");

        progressBar.setStyle("-fx-color: " + getColor());
    }

    private String getColor() {
        if (value < 0.2) {
            return "lightgreen";
        } else if (value < 0.4) {
            return "green";
        } else if (value < 0.60) {
            return "orange";
        } else if (value < 0.8) {
            return "red";
        } else {
            return "darkred";
        }
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return radioBtn.isSelected();
    }

    public void setSelected(boolean selected) {
        radioBtn.setSelected(selected);
    }

    public void setGroup(ToggleGroup group) {
        radioBtn.setToggleGroup(group);
    }


}
