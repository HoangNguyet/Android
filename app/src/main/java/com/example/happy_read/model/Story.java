package com.example.happy_read.model;

import java.util.Date;

public class Story {
    private int id;
    private String title;
    private String description;
    private String content;
    private String genre;
    private Date createdAt;
    private Date updateAt;
    private String imagePathDes;
    private int view;
    private User user;

    public Story() {
    }

    public Story(String title, String imagePathDes) {
        this.title = title;
        this.imagePathDes = imagePathDes;
    }

    public Story(int id, String title, String description, String content, String genre, String imagePathDes, int view, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.genre = genre;
        this.imagePathDes = imagePathDes;
        this.view = view;
        this.user = user;
    }

    public Story(int id, String title, String content, String imagePathDes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePathDes = imagePathDes;
    }

    public Story(int id, String title, String genre, String imagePathDes, int view) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.imagePathDes = imagePathDes;
        this.view = view;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getImagePathDes() {
        return imagePathDes;
    }

    public void setImagePathDes(String imagePathDes) {
        this.imagePathDes = imagePathDes;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
