package com.optogo.graphics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GraphNode {
    private List<Link> links;
    private String name, text;
    private Double weight;

    public GraphNode(String name) {
        this.name = name;
        this.text = name;
        this.links = new ArrayList<>();
    }

    public void link(GraphNode node, Double weight) {
        links.add(new Link(this, node, weight));
    }

    public void link(GraphNode node) {
        link(node, null);
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Double getWeight() {
        return weight;
    }

    public static class Builder {
        private GraphNode node;
        private LinkedHashMap<String, Double> links;

        public Builder(String name) {
            this.node = new GraphNode(name);
            this.links = new LinkedHashMap<>();
        }

        public static Builder create(String name) {
            return new Builder(name);
        }

        public String getName() {
            return node.getName();
        }

        public Builder setWeight(Double weight) {
            if(weight <= 1)
                this.node.weight = weight * 100d;
            else
                this.node.weight = weight;

            return this;
        }

        public Builder setWeight(Float weight) {
            return setWeight(Double.valueOf(weight));
        }

        public Builder setText(String text) {
            this.node.text = text;
            return this;
        }

        public Builder link(String node, Double weight) {
            links.put(node, weight);
            return this;
        }

        public Builder link(String node) {
            links.put(node, null);
            return this;
        }

        public LinkedHashMap<String, Double> getLinks() {
            return links;
        }

        public GraphNode getNode() {
            return node;
        }
    }

}
