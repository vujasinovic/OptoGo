package com.optogo.view.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class OptionSelection extends HBox {
    private static String SOURCE = "fxml/option_select.fxml";

    @FXML
    private Label lblName;

    @FXML
    private RadioButton btnYes;

    @FXML
    private RadioButton btnNo;

    private String name;

    public OptionSelection(String name) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource(SOURCE));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        this.name = name;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        init();
    }

    private void init() {
        lblName.setText(name);

        btnNo.setSelected(true);

        ToggleGroup group = new ToggleGroup();
        btnNo.setToggleGroup(group);
        btnYes.setToggleGroup(group);
    }

    public void setValue(boolean value) {
        if (value) {
            btnYes.setSelected(true);
        } else {
            btnNo.setSelected(true);
        }
    }

    public boolean getValue() {
        return btnYes.isSelected();
    }

    public String getName() {
        return name;
    }

}
