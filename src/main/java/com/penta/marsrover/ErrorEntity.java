package com.penta.marsrover;

import java.io.Serializable;

public class ErrorEntity implements Serializable {
    String message;

    public ErrorEntity() {
    }

    public ErrorEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
