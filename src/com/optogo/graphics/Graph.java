package com.optogo.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
    private List<Node> nodes;

    public Graph() {
        this.nodes = new ArrayList<>();
    }

    public Node getNode(String name) {
        for (Node node : nodes) {
            if (node.getName().equalsIgnoreCase(name)) {
                return node;
            }
        }

        return null;
    }

    public void add(Node node) {
        if (getNode(node.getName()) != null) {
            throw new IllegalArgumentException("Node '" + node.getText() + "' already exists in graph.");
        }
        this.nodes.add(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public static class Builder {
        private Graph graph;
        private List<Node.Builder> nodeBuilders;


        public Builder() {
            this.graph = new Graph();
            this.nodeBuilders = new ArrayList<>();
        }

        public static Builder create() {
            return new Builder();
        }

        public Graph.Builder addNode(Node.Builder nodeBuilder) {
            this.nodeBuilders.add(nodeBuilder);
            return this;
        }

        public Graph build() {
            for (Node.Builder nodeBuilder : nodeBuilders) {
                Node node = nodeBuilder.getNode();
                graph.add(node);
            }

            for (Node.Builder nodeBuilder : nodeBuilders) {

                HashMap<String, Double> links = nodeBuilder.getLinks();
                for (String nodeName : links.keySet()) {
                    Node linkTo = graph.getNode(nodeName);
                    Double weight = links.get(nodeName);
                    nodeBuilder.getNode().link(linkTo, weight);
                }

            }

            return graph;
        }
    }
}
