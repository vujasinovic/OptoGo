package com.optogo.service.bayes;

public class ProbabilityPair {
    private double yes;
    private double no;

    public ProbabilityPair() {
        setNo(1);
    }

    public void setYes(double yes) {
        if(yes > 1)
            throw new IllegalArgumentException("Only values [0, 1] are allowed");

        this.yes = yes;
        this.no = 1 - yes;
    }

    public void setNo(double no) {
        if(yes > 1)
            throw new IllegalArgumentException("Only values [0, 1] are allowed");

        this.no = no;
        this.yes = 1 - no;
    }

    public double getYes() {
        return yes;
    }

    public double getNo() {
        return no;
    }
}
