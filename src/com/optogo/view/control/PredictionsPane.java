package com.optogo.view.control;

import com.optogo.utils.StringFormatter;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PredictionsPane extends BorderPane {
    private List<PredictedCondition> predictedConditions;

    private ToggleGroup group;

    public PredictionsPane() {
        this.predictedConditions = new ArrayList<>();
        group = new ToggleGroup();
    }

    public void add(Map<String, Float> predictions, String name, PredictedCondition.Mode selectionMode) {
        VBox vbox = new VBox();
        vbox.setSpacing(5);

        predictedConditions.clear();

        for (String key : predictions.keySet()) {
            PredictedCondition predictedCondition = new PredictedCondition(key, predictions.get(key), selectionMode);
            this.predictedConditions.add(predictedCondition);

            if(selectionMode == PredictedCondition.Mode.SINGLE)
                predictedCondition.setGroup(group);
            vbox.getChildren().add(predictedCondition);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(vbox);
        scrollPane.setPadding(new Insets(5));
        setCenter(scrollPane);
    }

    public List<String> getAllSelected() {
        List<String> selection = new ArrayList<>();

        for (PredictedCondition condition : predictedConditions) {
            if(condition.isSelected())
                selection.add(StringFormatter.underscoredLowerCase(condition.getName()));
        }

        return selection;
    }

    public String getSelected() {
        for (PredictedCondition condition : predictedConditions) {
            if(condition.isSelected())
                return StringFormatter.underscoredLowerCase(condition.getName());
        }

        return null;
    }

    public ToggleGroup getGroup() {
        return group;
    }

    public void setGroup(ToggleGroup group) {
        this.group = group;
    }

}
