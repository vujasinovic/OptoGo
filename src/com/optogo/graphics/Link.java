package com.optogo.graphics;

public class Link {
    private Node from, to;
    private Double weight;

    public Link(Node from, Node to, Double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Link(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public Double getWeight() {
        return weight;
    }

}
