package com.optogo.service.bayes;

public interface HandlerProgressListener {

    void progressUpdated(int current, int max, String message);

}
