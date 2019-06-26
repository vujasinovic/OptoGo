package com.optogo.graphics;

public class Link {
    private GraphNode from, to;
    private Double weight;

    public Link(GraphNode from, GraphNode to, Double weight) {
        this.from = from;
        this.to = to;

        if (weight <= 1)
            this.weight = weight * 100d;
        else
            this.weight = weight;
    }

    public Link(GraphNode from, GraphNode to) {
        this.from = from;
        this.to = to;
    }

    public GraphNode getFrom() {
        return from;
    }

    public GraphNode getTo() {
        return to;
    }

    public Double getWeight() {
        return weight;
    }

}
