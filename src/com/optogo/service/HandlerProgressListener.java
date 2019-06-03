package com.optogo.service;

public interface HandlerProgressListener {

    void progressUpdated(int current, int max, String message);

}
