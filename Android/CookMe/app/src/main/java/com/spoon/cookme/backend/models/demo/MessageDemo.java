package com.spoon.cookme.backend.models.demo;

/**
 * Created by Lycha on 9/20/2015.
 */
public class MessageDemo {

    private String message;
    private long timestamp;

    public MessageDemo() {
    }

    public MessageDemo(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
