package com.ronnie.topscore.exception;

public class ScoreNotFoundException extends RuntimeException {
    public ScoreNotFoundException(Long id) {
        super(String.format("Score [%d] not found.", id));
    }
}
