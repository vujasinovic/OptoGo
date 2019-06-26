package com.optogo.graphics;

import com.optogo.utils.StringFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
    private List<GraphNode> nodes;

    public Graph() {
        this.nodes = new ArrayList<>();
    }

    public GraphNode getNode(String name) {
        for (GraphNode node : nodes) {
            if (node.getName().equalsIgnoreCase(name)) {
                return node;
            }
        }

        return null;
    }

    public void add(GraphNode node) {
        if (getNode(node.getName()) != null) {
            throw new IllegalArgumentException("GraphNode '" + node.getText() + "' already exists in graph.");
        }
        this.nodes.add(node);
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public static class Builder {
        private Graph graph;
        private List<GraphNode.Builder> nodeBuilders;

        public Builder() {
            this.graph = new Graph();
            this.nodeBuilders = new ArrayList<>();
        }

        public static Builder create() {
            return new Builder();
        }

        public Graph.Builder addNode(GraphNode.Builder nodeBuilder) {
            this.nodeBuilders.add(nodeBuilder);
            return this;
        }

        private GraphNode.Builder getNode(String name) {
            for (GraphNode.Builder nodeBuilder : nodeBuilders) {
                if (nodeBuilder.getName().equals(name))
                    return nodeBuilder;
            }

            return null;
        }

        public Builder link(String from, Double weight, String to) {
            GraphNode.Builder nodeFrom = getNode(from);
            if (nodeFrom == null) {
                addNode(GraphNode.Builder.create(from).setText(StringFormatter.capitalizeWord(from)));
                nodeFrom = getNode(from);
            }
            nodeFrom.link(to, weight);

            return this;
        }

        public Builder link(String from, Float weight, String to) {
            return link(from, Double.valueOf(weight), to);
        }

        public Graph build() {
            for (GraphNode.Builder nodeBuilder : nodeBuilders) {
                GraphNode node = nodeBuilder.getNode();
                graph.add(node);
            }

            for (GraphNode.Builder nodeBuilder : nodeBuilders) {

                HashMap<String, Double> links = nodeBuilder.getLinks();
                for (String nodeName : links.keySet()) {
                    GraphNode linkTo = graph.getNode(nodeName);
                    Double weight = links.get(nodeName);
                    nodeBuilder.getNode().link(linkTo, weight);
                }

            }

            return graph;
        }
    }
}
