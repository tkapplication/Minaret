package com.example.khalid.minaret.models;

/**
 * Created by khalid on 12/16/2017.
 */

public class Post {
    public Post() {
    }

    public Post(String id, String title, String content, String date, String image, String comment, String comment_count, String favorite_count) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.image = image;
        this.comment = comment;
        this.comment_count = comment_count;
        this.favorite_count = favorite_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(String favorite_count) {
        this.favorite_count = favorite_count;
    }

    String id, title, content, date, image, comment, comment_count, favorite_count;


}
