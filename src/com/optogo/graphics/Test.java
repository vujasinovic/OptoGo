package com.optogo.graphics;

import java.awt.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Graph graph = Graph.Builder.create()
                .addNode(GraphNode.Builder.create("node1").setWeight(0.2512).link("node2", 25.5).link("node3", 98.12))
                .addNode(GraphNode.Builder.create("node2").setWeight(0.3))
                .addNode(GraphNode.Builder.create("node3").setWeight(0.9012).link("node4"))
                .addNode(GraphNode.Builder.create("node4").setWeight(0.1))
                .build();

        GraphRenderer renderer = new GraphRenderer();
        renderer.render(graph);

        Desktop.getDesktop().open(renderer.getFile());
    }
}
