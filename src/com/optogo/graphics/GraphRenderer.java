package com.optogo.graphics;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class GraphRenderer {
    private File imageFile;

    public void render(Graph graph) throws IOException {
        guru.nidi.graphviz.model.Graph gvGraph = graph().directed().graphAttr()
                .with(RankDir.LEFT_TO_RIGHT, GraphAttr.splines(GraphAttr.SplineMode.SPLINE));

        for (GraphNode node : graph.getNodes()) {
            guru.nidi.graphviz.model.Node gvNode = node(node.getName()).with(Color.BLACK);
            for (Link link : node.getLinks()) {
                guru.nidi.graphviz.model.Link gvLink = to(node(link.getTo().getName()));
                if (link.getWeight() != null) {
                    gvLink = gvLink.with(Style.BOLD, Label.of(formatWeight(link.getWeight())));
                }
                gvNode = gvNode.link(gvLink);
            }
            if (node.getWeight() != null) {
                gvNode = gvNode.with(Label.of(node.getText() + "\n" + formatWeight(node.getWeight()))
                        , getColor(node.getWeight()), Style.BOLD);
            }

            gvGraph = gvGraph.with(gvNode);
        }

        this.imageFile = createOutputFile();
        Graphviz.fromGraph(gvGraph).width(1200).render(Format.PNG).toFile(imageFile);
    }

    public String formatWeight(Double weight) {
        return String.format("%.2f%%", weight);
    }

    private File createOutputFile() throws IOException {
        File tmpFile = Files.createTempFile("graphviz-", ".png").toFile();
        return tmpFile;
    }

    private Color getColor(Double value) {
        if (value < 20) {
            return Color.GREEN;
        } else if (value < 40) {
            return Color.GREEN4;
        } else if (value < 60) {
            return Color.ORANGE;
        } else if (value < 80) {
            return Color.RED;
        } else {
            return Color.RED4;
        }
    }

    public File getFile() {
        return imageFile;
    }

}
