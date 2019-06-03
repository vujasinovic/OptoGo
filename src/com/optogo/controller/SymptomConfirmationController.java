package com.optogo.controller;

import com.optogo.view.control.OptionSelection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SymptomConfirmationController {
    @FXML
    private VBox container;

    public void initialize() {
        container.getChildren().addAll(
                new OptionSelection("123"),
                new OptionSelection("456"),
                new OptionSelection("789"),
                new Button("123")
        );
    }



}
