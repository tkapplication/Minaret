package com.example.khalid.minaret.models;

/**
 * Created by khalid on 1/20/2018.
 */

public class Comment {
    String content;
    String date;

    public Comment(String content, String date) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
