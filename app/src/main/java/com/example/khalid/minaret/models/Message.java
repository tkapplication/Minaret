package com.example.khalid.minaret.models;

/**
 * Created by khalid on 1/16/2018.
 */

public class Message {
    public Message(String title, String message) {
        this.title = title;
        this.message = message;
    }

    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;
}
