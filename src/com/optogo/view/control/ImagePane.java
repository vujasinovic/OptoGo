package com.optogo.view.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;

public class ImagePane extends Pane {
    public ImagePane(String imageLoc) {
        this(imageLoc, "-fx-background-size: auto; -fx-background-repeat: no-repeat; -fx-background-position: center;");
    }

    public ImagePane(String imageLoc, String style) {
        this(new SimpleStringProperty(imageLoc), new SimpleStringProperty(style));
    }

    ImagePane(StringProperty imageLocProperty, StringProperty styleProperty) {
        styleProperty().bind(
                new SimpleStringProperty("-fx-background-image: url(\"")
                        .concat(imageLocProperty)
                        .concat(new SimpleStringProperty("\");"))
                        .concat(styleProperty)
        );
    }
}
