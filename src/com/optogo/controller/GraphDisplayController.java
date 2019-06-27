package com.optogo.controller;

import com.optogo.graphics.Graph;
import com.optogo.graphics.GraphRenderer;
import com.optogo.view.control.ImagePane;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphDisplayController {
    public ScrollPane container;
    public ImageView imgView;

    public void initialize() {
    }

    public void show(Graph graph) {
        GraphRenderer renderer = new GraphRenderer();
        try {
            renderer.render(graph);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        File imgFile = renderer.getFile();
        BufferedImage image = null;
        try {
            image = ImageIO.read(imgFile);
            imgView.maxHeight(image.getHeight());
            imgView.maxWidth(image.getWidth());
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgView.setImage(new Image(imgFile.toURI().toString()));
    }

    public void close(ActionEvent actionEvent) {
        getStage(actionEvent).close();
    }

    private Stage getStage(Event event) {
        return (Stage) ((Control) event.getSource()).getScene().getWindow();
    }
}
